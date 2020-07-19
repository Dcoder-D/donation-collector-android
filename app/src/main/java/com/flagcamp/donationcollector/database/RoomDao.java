package com.flagcamp.donationcollector.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.flagcamp.donationcollector.model.Item;

import java.util.List;

@Dao
public interface RoomDao {
    @Insert
    void saveItem(Item item);

    @Query("SELECT * FROM item")
    LiveData<List<Item>> getAllItems();

}
