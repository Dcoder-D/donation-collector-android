package com.flagcamp.donationcollector.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.model.Location;
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

    @Query("SELECT * FROM appUser")
    List<AppUser> getAppUserList();

    @Query("SELECT * FROM item WHERE status LIKE :keyword")
    LiveData<List<Item>> getSpecificItems(String keyword);

    @Query("UPDATE item SET status = :next WHERE status LIKE :prev")
    void updateStatus(String next, String prev);

    @Delete
    void deleteAppUser(AppUser appUser);

    @Query("DELETE FROM item")
    void deleteAllItems();

    @Query("DELETE FROM item WHERE status = :condition")
    void deleteSelectedItems(String condition);

//    @Query("SELECT * FROM location")
//    LiveData<List<Location>> getAllLocation();
//
//    @Query("DELETE FROM location")
//    void deleteAllLocation();
//
//    @Insert
//    void saveLocation(Location location);

}
