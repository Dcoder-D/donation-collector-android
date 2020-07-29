package com.flagcamp.donationcollector.repository;

import androidx.room.TypeConverter;

import com.flagcamp.donationcollector.model.PosterUser;
import com.flagcamp.donationcollector.model.PosterUserReceived;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PosterUserReceivedConverters {
    @TypeConverter
    public static PosterUserReceived fromString(String value) {
        Type postUserReceivedType = new TypeToken<PosterUserReceived>(){}.getType();
        return new Gson().fromJson(value, postUserReceivedType);
    }

    @TypeConverter
    public static String fromPosterUserReceived(PosterUserReceived posterUser) {
        Gson gson = new Gson();
        String json = gson.toJson(posterUser);
        return json;
    }
}
