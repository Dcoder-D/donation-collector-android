package com.flagcamp.donationcollector.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flagcamp.donationcollector.DonationCollectorApplication;
import com.flagcamp.donationcollector.database.AppDatabase;
import com.flagcamp.donationcollector.database.RoomDao;
import com.flagcamp.donationcollector.signin.AppUser;

import java.util.ArrayList;
import java.util.List;

public class SignInRepository {

    private final AppDatabase database;
    private final RoomDao dao;
    private LiveData<List<AppUser>> appUsers;
    private List<AppUser> appUsersList;

    enum Operation {
        WRITE,
        DELETE
    }

    public SignInRepository() {
        database = DonationCollectorApplication.getDatabase();
        dao = database.dao();
        appUsers = dao.getAppUser();
//        appUsersList = dao.getAppUserList();
    }

    public void saveAppUser(AppUser appUser) {
        new InsertAsyncTask(dao, Operation.WRITE).execute(appUser);
    }

    public void deleteAppUser(AppUser appUser) {
        new InsertAsyncTask(dao, Operation.DELETE).execute(appUser);
    }

    public LiveData<List<AppUser>> getAppUser() {
        return appUsers;
    }

//    public List<AppUser> getAppUsersList() {
////        database = DonationCollectorApplication.getDatabase();
////        dao = database.dao();
//        return dao.getAppUserList();
////        return appUsersList;
//    }

    private static class InsertAsyncTask extends AsyncTask<AppUser, Void, Void>{
        RoomDao dao;
        Operation op;

        public InsertAsyncTask(RoomDao dao, Operation op) {
            this.dao = dao;
            this.op = op;
        }

        @Override
        protected Void doInBackground(AppUser... appUsers) {
            switch (op) {
                case WRITE:
                    dao.saveAppUser(appUsers[0]);
                    break;
                case DELETE:
                    dao.deleteAppUser(appUsers[0]);
                    break;
            }
            return null;
        }
    }

//    private static class ReadAsyncTask extends  AsyncTask<Void, Void, List<AppUser>> {
//
//        @Override
//        protected List<AppUser> doInBackground(Void... voids) {
//            return null;
//        }
//    }


}