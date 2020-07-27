package com.flagcamp.donationcollector.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.Converters;
import com.flagcamp.donationcollector.signin.AppUser;

@Database(entities = {Item.class, AppUser.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract RoomDao dao();
}
