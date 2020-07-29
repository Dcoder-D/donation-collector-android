package com.flagcamp.donationcollector.network;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.model.PostBody;
import com.flagcamp.donationcollector.model.PostItem;
import com.flagcamp.donationcollector.model.PostResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.POST;

public interface PostApi {

    @GET("userPosts")
    Call<List<Item>> getUserPosts(@Query("userId") String posterId);

    @GET("searchPostsNGO")
    Call<List<Item>> getPostsByLocation(@Query("address") String location, @Query("distance") String distance);

    @GET("items")
    Call<List<Item>> getNGOPickUp(@Query("pickUpNGOId") String pickUpNGOId);

    @GET("items")
    Call<List<Item>> getStatusEquals(@Query("status") String status);

    @POST("deleteItem")
    Call<List<Item>> deletePost(@Query("userId") String userId, @Query("itemId") String itemId);

    @Multipart
    @POST("createPost")
    Call<ResponseBody> createPost(@Part("TestText") RequestBody TestText, @Part MultipartBody.Part file);
}
