package com.flagcamp.donationcollector;

import android.app.Application;

import androidx.room.Room;

import com.flagcamp.donationcollector.signin.database.LoginDatabase;

public class DonationCollectorApplication extends Application {
    private static LoginDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), LoginDatabase.class, "login-db").build();
    }

    public static LoginDatabase getDatabase() {
        return database;
    }

}
