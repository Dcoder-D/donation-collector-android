package com.flagcamp.donationcollector.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.signin.AppUser;

import java.util.List;

@Dao
public interface RoomDao {
    @Insert
    void saveItem(Item item);

    @Insert
    void saveAppUser(AppUser appUser);

    @Query("SELECT * FROM item")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * FROM appUser")
    LiveData<List<AppUser>> getAppUser();

    @Delete
    void deleteAppUser(AppUser appUser);
}
