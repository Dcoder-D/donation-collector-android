package com.flagcamp.donationcollector.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;

import com.flagcamp.donationcollector.DonationCollectorApplication;
import com.flagcamp.donationcollector.database.AppDatabase;
import com.flagcamp.donationcollector.database.RoomDao;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.model.Location;
import com.flagcamp.donationcollector.model.PostBody;
import com.flagcamp.donationcollector.model.PostItem;
import com.flagcamp.donationcollector.model.PostResponse;
import com.flagcamp.donationcollector.network.PostApi;
import com.flagcamp.donationcollector.network.RetrofitClient;
import com.flagcamp.donationcollector.signin.AppUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {

    private final PostApi postApi;
    private final AppDatabase database;
    private final RoomDao dao;
    private AsyncTask asyncTask;

    public PostRepository(Context context) {
        postApi = RetrofitClient.newInstance(context).create(PostApi.class);
        database = DonationCollectorApplication.getDatabase();
        dao = database.dao();
    }

//    public LiveData<List<Location>> getAllLocation() {
//        return dao.getAllLocation();
//    }
//
//    public void deleteAllLocation() {
//        dao.deleteAllLocation();
//    }
//
//    public void insertLocation(Location location) {
//        dao.saveLocation(location);
//    }

    public Response createPost(List<PostItem> items, List<String> imagePaths) {
//

        final Response[] myResponse = new Response[1];

        PostBody postBody = new PostBody();
        postBody.TestText = items;

        RequestBody postBodyText = RequestBody.create(MediaType.parse("text/plain"), postBody.toString());

        Log.d("PostRepository", "postBodyText: " + postBody.toString());

        Map<String, RequestBody> params = new HashMap<>();
        params.put("TestText", postBodyText);

        File[] files = new File[imagePaths.size()];
        for(int i = 0; i < imagePaths.size(); i++) {
            files[i] = new File(imagePaths.get(i));
//            RequestBody imageBody = RequestBody.create(MediaType.parse("image/png"), files[i]);
//            params.put(String.format("image%d\"; filename=\"f%d.png\"" , i,  i), imageBody);
//
//            Log.d("PostRepository", "imageBody: " + imageBody.toString());
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), files[0]);
        MultipartBody.Part fileBody = MultipartBody.Part.createFormData("image", files[0].getName(), requestFile);


        MultipartBody.Part[] fileParts = new MultipartBody.Part[files.length];
        for(int i = 0; i < fileParts.length; i++) {
            fileParts[i] = MultipartBody.Part.createFormData("image" + i, files[i].getName(), RequestBody.create(MediaType.parse("image/jpg"), files[i]));
        }

        Log.d("PostRepository", "file: " + files[0].getName() + ", " + requestFile.toString());


        postApi.createPost(postBodyText, fileParts).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    myResponse[0] = response;
                    Log.d("PostRepository", "createPost is successful with code: " + response.code());
                    if(response.code() == 200 || response.code() == 201) {
                        Log.d("PostRepository", "create Post returns " + response.code() + response.message());
                    }
                } else {
                    myResponse[0] = response;
                    Log.d("PostRepository", "createPost unSuccessful with code: " + response.code() + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("createPost", t.toString());
            }

        });
        deleteAddedItems();
        return myResponse[0];
    }

    public LiveData<ResponseBody> createRoute(RequestBody body) {
        final MutableLiveData<ResponseBody> routeData = new MutableLiveData<>();
        final ResponseBody[] responseBodies = new ResponseBody[1];

        postApi.createRoute(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Log.d("PostRepository", "createRoute successfull with code: " + response.code());
                    if(response.body() != null) {
                        responseBodies[0] = response.body();
                        routeData.setValue(response.body());
                        try {
                            Log.d("PostRepository", "createRoute response body: " + responseBodies[0].string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        routeData.setValue(null);
                        Log.d("PostRepository", "createRoute response body is null");
                    }


                } else {
                    Log.d("PostRepository", "createRoute not successful with code: " + response.code());
                    routeData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                routeData.setValue(null);
                Log.d("PostRepository", "createRoute failed, " + t.toString());
            }
        });
//        return responseBodies[0].toString();
        return routeData;
    }

    public LiveData<List<Item>> getPostsByLocation(String location, String distance) {
        final MutableLiveData<List<Item>> postsByLocation = new MutableLiveData<>();
        postApi.getPostsByLocation(location, distance).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()) {
                    postsByLocation.setValue(response.body());
                    Log.d("getPostsByLocation", response.body().toString());
                } else {
                    postsByLocation.setValue(null);
                    Log.d("getPostsByLocation", "response unsuccessful, " + response.code() + ", " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.d("getPostsByLocation", "error: " + t.toString());
                postsByLocation.setValue(null);
            }
        });
        return postsByLocation;
    }

    public LiveData<List<Item>> getUserPosts(String posterId) {
        final MutableLiveData<List<Item>> userPostsLiveData = new MutableLiveData<>();
        postApi.getUserPosts(posterId).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()) {
                    userPostsLiveData.setValue(response.body());
//                    Log.d(response.body().toString());
                    System.out.println(response.body());
                    Log.d("getUserPosts", response.body().toString());
                } else {
                    userPostsLiveData.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                userPostsLiveData.setValue(null);
            }
        });
        return userPostsLiveData;
    }

    public LiveData<List<Item>> getNGOPosts(String ngoId) {
        final MutableLiveData<List<Item>> ngoPostsLiveData = new MutableLiveData<>();
        postApi.getNGOPosts(ngoId).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()) {
                    ngoPostsLiveData.setValue(response.body());
                    Log.d("getNGOPosts", response.body().toString());
                } else {
                    ngoPostsLiveData.setValue(null);
                    Log.d("getNGOPosts", "not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                ngoPostsLiveData.setValue(null);
                Log.d("getNGOPosts", "failed, " + t.toString());
            }
        });
        return ngoPostsLiveData;
    }

    public LiveData<List<Item>> getNGOPickUp(String pickUpNGOId) {
        final MutableLiveData<List<Item>> userPostsLiveData = new MutableLiveData<>();
        postApi.getNGOPickUp(pickUpNGOId).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()) {
                    userPostsLiveData.setValue(response.body());
//                    Log.d(response.body().toString());
                    System.out.println(response.body());
                    Log.d("getNGOPickUp", response.body().toString());
                } else {
                    userPostsLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                userPostsLiveData.setValue(null);
            }
        });
        return userPostsLiveData;
    }

    public LiveData<List<Item>> getStatusEquals(String status) {
        final MutableLiveData<List<Item>> statusEqualsLiveData = new MutableLiveData<>();
        postApi.getStatusEquals(status).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()) {
                    statusEqualsLiveData.setValue(response.body());
                } else {
                    statusEqualsLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                statusEqualsLiveData.setValue(null);
            }
        });

        return statusEqualsLiveData;
    }

        //TODO:getDateEquals()

    public LiveData<List<Item>> getNGODateEquals(String pickUpDate, String pickUpNGOID) {
        final MutableLiveData<List<Item>> dateEqualsLiveData = new MutableLiveData<>();
        postApi.getNGODateEquals(pickUpDate, pickUpNGOID).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()) {
                    dateEqualsLiveData.setValue(response.body());
                    Log.d("NGOPostRepoDates", "Success");
                    Log.d("NGOPostRepoDates", response.body().toString());
                } else {
                    dateEqualsLiveData.setValue(null);
                    Log.d("NGOPostRepoDates", "Failed");
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                dateEqualsLiveData.setValue(null);
                Log.d("getNGODateEquals", "Failed: " + t.toString());
            }
        });

        return dateEqualsLiveData;
    }

    public LiveData<List<Item>> getUserDateEquals(String pickUpDate, String posterId) {
        final MutableLiveData<List<Item>> dateEqualsLiveData = new MutableLiveData<>();
        postApi.getUserDateEquals(pickUpDate, posterId).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()) {
                    dateEqualsLiveData.setValue(response.body());
                    Log.d("UserPostRepoDates", "Success");
                    Log.d("UserPostRepoDates", response.body().toString());
                } else {
                    dateEqualsLiveData.setValue(null);
                    Log.d("UserPostRepoDates", "not successful");
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                dateEqualsLiveData.setValue(null);
                Log.d("getUserDateEquals", "Failed: " + t.toString());
            }
        });

        return dateEqualsLiveData;
    }

    public LiveData<Response> deletePost(String userId, String itemId) {
        final MutableLiveData<Response> deletePostResponse = new MutableLiveData<>();
        Log.d("PostRepository", "userId: " + userId + ", itemId: " + itemId);
        postApi.deletePost(userId, itemId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    deletePostResponse.setValue(response);
                    Log.d("PostRepository", "deletePostResponse success with code: " + response.code());
                } else {
                    deletePostResponse.setValue(response);
                    Log.d("PostResitory", "deletePostResponse unsuccessful with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                deletePostResponse.setValue(null);
                Log.d("PostRepository", "failed with throwable: " + t.toString());
            }
        });

        return deletePostResponse;
    }
    public LiveData<Response> schedulePickUp(String itemId, String ngoId, String ngoName, String pickUpDate){
        final MutableLiveData<Response> schedulePickUpResponse = new MutableLiveData<>();
        postApi.schedulePickUp(itemId, ngoId, ngoName, pickUpDate).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()) {
                    schedulePickUpResponse.setValue(response);
                    Log.d("PostRepository", "schedulePickUp successful, code: " + response.code());
                } else {
                    schedulePickUpResponse.setValue(response);
                    Log.d("PostRepository", "schedulePickUp not successful, code: " + response.code() + ", message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("PostRepository", "schedulePickUp failure,  " + t.toString());
                schedulePickUpResponse.setValue(null);

            }
        });
        return schedulePickUpResponse;
    }

    public LiveData<Response> confirmPickUp(String itemId, String ngoId){
        final MutableLiveData<Response> confirmPickUpResponse = new MutableLiveData<>();
        postApi.confirmPickUp(itemId, ngoId).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()) {
                    confirmPickUpResponse.setValue(response);
                    Log.d("PostRepository", "confirmPickUp successful, code: " + response.code());
                } else {
                    confirmPickUpResponse.setValue(response);
                    Log.d("PostRepository", "confirmPickUp not successful, code: " + response.code() + ", message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("PostRepository", "confirmPickUp failure,  " + t.toString());
                confirmPickUpResponse.setValue(null);

            }
        });
        return confirmPickUpResponse;
    }
    // connect Room database for PostsPreviewFragment
    public void addItemAsAdded(Item item) {
        new InsertAsyncTask(dao).execute(item);
    }

    public LiveData<List<Item>> getAddedItems() {
        return dao.getSpecificItems("added");
    }

    public void postAllAddedItem() {
        // first, post all added items to backend service
        // TODO: POST all items to backend
        // if the post is success, change all item status to pending from added
        // assume success, change all item status from added to pending
        deleteAddedItems();
    }

    private void deleteAddedItems() {
        new DeleteAsyncTask(dao, "added").execute();
    }

    public void deleteAllItems() {
        new DeleteAsyncTask(dao, "").execute();
    }

//    public static void main(String[] args) {
//        PostRepository postRepository = new PostRepository();
//        postRepository.getUserPosts("0");
//    }

    private static class InsertAsyncTask extends AsyncTask<Item, Void, Void>{
        RoomDao dao;

        public InsertAsyncTask(RoomDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            dao.saveItem(items[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Void, Void, Void>{
        RoomDao dao;
        String next, prev;

        public UpdateAsyncTask(RoomDao dao, String next, String prev) {
            this.dao = dao;
            this.next = next;
            this.prev = prev;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.updateStatus(next, prev);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Void>{
        RoomDao dao;
        String status;

        public DeleteAsyncTask(RoomDao dao, String status) {
            this.dao = dao;
            this.status = status;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (status.equals("")) {
                dao.deleteAllItems();
            } else {
                dao.deleteSelectedItems(status);
            }
            return null;
        }
    }
}
