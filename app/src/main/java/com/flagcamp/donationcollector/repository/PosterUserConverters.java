package com.flagcamp.donationcollector.repository;

import androidx.room.TypeConverter;

import com.flagcamp.donationcollector.model.PosterUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PosterUserConverters {
    @TypeConverter
    public static PosterUser fromString(String value) {
        Type postUserType = new TypeToken<PosterUser>(){}.getType();
        return new Gson().fromJson(value, postUserType);
    }

    @TypeConverter
    public static String fromPosterUser(PosterUser posterUser) {
        Gson gson = new Gson();
        String json = gson.toJson(posterUser);
        return json;
    }
}
