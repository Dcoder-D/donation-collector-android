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
//        MultipartBody.Part[] fileParts = new MultipartBody.Part[files.length];
//        for(int i = 0; i < fileParts.length; i++) {
//            fileParts[i] = MultipartBody.Part.createFormData("image" + i, files[i].getName(), RequestBody.create(MediaType.parse("image/*"), files[i]));
//        }

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

        Log.d("PostRepository", "file: " + files[0].getName() + ", " + requestFile.toString());


        postApi.createPost(postBodyText, fileBody).enqueue(new Callback<ResponseBody>() {
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
    public LiveData<List<Item>> getDateEquals(String pickUpDate) {
        final MutableLiveData<List<Item>> dateEqualsLiveData = new MutableLiveData<>();
        postApi.getDateEquals(pickUpDate).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()) {
                    dateEqualsLiveData.setValue(response.body());
                    Log.d("PostRepoDates", "Success");
                    Log.d("PostRepoDates", response.body().toString());
                } else {
                    dateEqualsLiveData.setValue(null);
                    Log.d("PostRepoDates", "Failed");
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                dateEqualsLiveData.setValue(null);
                Log.d("getDateEquals", "Failed: " + t.toString());
            }
        });

        return dateEqualsLiveData;
    }

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

    public List<Item> getUserPostsList(String posterId) {
        List<Item> userPostsLiveData = new ArrayList<>();
        postApi.getUserPosts(posterId).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.isSuccessful()) {
                    userPostsLiveData.addAll(response.body());
//                    Log.d(response.body().toString());
                    System.out.println(response.body());
                    Log.d("response", response.body().toString());
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
        return userPostsLiveData;
    }

    public Boolean deletePost(String userId, String itemId) {
        final Boolean[] deleteRes = {false};
        postApi.deletePost(userId, itemId).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()) {
                    deleteRes[0] = true;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                deleteRes[0] = false;
            }
        });

        return deleteRes[0];
    }
    public Boolean confirmPickUp(String itemId, String ngoId){
        final Boolean[] confirmRes = {false};
        postApi.confirmPickUp(itemId, ngoId).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()) {
                    confirmRes[0] = true;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                confirmRes[0] = false;
            }
        });

        return confirmRes[0];
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
