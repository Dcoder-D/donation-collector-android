package com.flagcamp.donationcollector.signin.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.flagcamp.donationcollector.signin.AppUser;

import java.util.List;

@Dao
public interface RoomDao {
    @Insert
    void saveAppUser(AppUser appUser);

    @Query("SELECT * FROM appUser")
    LiveData<List<AppUser>> getAppUser();

    @Delete
    void deleteAppUser(AppUser... appUser);
}
