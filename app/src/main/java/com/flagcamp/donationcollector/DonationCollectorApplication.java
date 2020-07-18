package com.flagcamp.donationcollector;

import android.app.Application;

import androidx.room.Room;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;
import com.facebook.stetho.Stetho;
import com.flagcamp.donationcollector.database.AppDatabase;

public class DonationCollectorApplication extends Application {
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        Gander.setGanderStorage(GanderIMDB.getInstance());
        Stetho.initializeWithDefaults(this);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "donation_db").build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
