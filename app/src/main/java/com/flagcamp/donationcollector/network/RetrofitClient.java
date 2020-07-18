package com.flagcamp.donationcollector.network;

import android.content.Context;

import com.ashokvarma.gander.GanderInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String API_KEY = "199f93ee0e234c8d952b8620aac052ad";
    private static final String BASE_URL = "http://10.0.2.2:3000/";
    private static final String NEWS_BASE_URL = "https://newsapi.org/v2/";

    public static Retrofit newInstance(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new HeaderInterceptor())
//                .addInterceptor(new GanderInterceptor(context)
//                        .showNotification(true))
//                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original
                    .newBuilder()
//                    .header("X-Api-Key", API_KEY)
                    .build();
            return chain.proceed(request);
        }
    }
}
