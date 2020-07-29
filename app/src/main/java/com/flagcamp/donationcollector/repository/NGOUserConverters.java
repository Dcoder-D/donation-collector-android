package com.flagcamp.donationcollector.repository;

import androidx.room.TypeConverter;

import com.flagcamp.donationcollector.model.NGOUser;
import com.flagcamp.donationcollector.model.PosterUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class NGOUserConverters {
    @TypeConverter
    public static NGOUser fromString(String value) {
        Type ngoUserType = new TypeToken<NGOUser>(){}.getType();
        return new Gson().fromJson(value, ngoUserType);
    }

    @TypeConverter
    public static String fromPosterUser(NGOUser ngoUser) {
        Gson gson = new Gson();
        String json = gson.toJson(ngoUser);
        return json;
    }
}
