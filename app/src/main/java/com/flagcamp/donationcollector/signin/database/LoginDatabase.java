package com.flagcamp.donationcollector.signin.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.flagcamp.donationcollector.signin.AppUser;

@Database(entities = {AppUser.class}, version = 1)
public abstract class LoginDatabase extends RoomDatabase {
    public abstract RoomDao dao();
}
