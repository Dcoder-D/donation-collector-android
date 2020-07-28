package com.flagcamp.donationcollector.network;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.model.PostResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.POST;

public interface PostApi {

    @GET("items")
    Call<List<Item>> getUserPosts(@Query("posterId") String posterId);

    @GET("items")
    Call<List<Item>> getNGOPickUp(@Query("pickUpNGOId") String pickUpNGOId);

    @GET("items")
    Call<List<Item>> getStatusEquals(@Query("status") String status);


    @GET("items")
    Call<List<Item>> getDateEquals(@Query("pickUpDate") String pickUpDate);

    @GET("items")
    Call<List<Item>> getNGODateEquals(@Query("pickUpDate") String pickUpDate,
                                      @Query("pickUpNGOId") String pickUpNGOID);

    @GET("items")
    Call<List<Item>> getUserDateEquals(@Query("pickUpDate") String pickUpDate,
                                      @Query("posterId") String posterId);

    @POST("items")
    Call<List<Item>> deletePost(@Query("itemId") String itemId);

}
