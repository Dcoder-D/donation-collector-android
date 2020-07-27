package com.flagcamp.donationcollector.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flagcamp.donationcollector.DonationCollectorApplication;
import com.flagcamp.donationcollector.database.AppDatabase;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.model.PostResponse;
import com.flagcamp.donationcollector.network.PostApi;
import com.flagcamp.donationcollector.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {

    private final PostApi postApi;
    private final AppDatabase database;
    private AsyncTask asyncTask;

    public PostRepository(Context context) {
        postApi = RetrofitClient.newInstance(context).create(PostApi.class);
        database = DonationCollectorApplication.getDatabase();
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

//    public static void main(String[] args) {
//        PostRepository postRepository = new PostRepository();
//        postRepository.getUserPosts("0");
//    }
}
