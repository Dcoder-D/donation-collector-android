package com.flagcamp.donationcollector.signin.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.flagcamp.donationcollector.DonationCollectorApplication;
import com.flagcamp.donationcollector.signin.AppUser;
import com.flagcamp.donationcollector.signin.database.LoginDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private final LoginDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private static final String TAG = "LoginRepository";

    public LoginRepository(Context context) {
        database = DonationCollectorApplication.getDatabase();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void logout(AppUser appUser) {
        mAuth.signOut();
        database.dao().deleteAppUser(appUser);
        user = null;
    }

    private void setAppUser(AppUser appUser) {
        database.dao().saveAppUser(appUser);
    }
}