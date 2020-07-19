package com.flagcamp.donationcollector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.signin.AppUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityUser extends AppCompatActivity {
    private NavController navControllerUser;
    AppUser appUser;
    private static final String TAG = "MainActivityUser";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        BottomNavigationView navViewUser = findViewById(R.id.nav_view_user);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_user_fragment);
        navControllerUser = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navViewUser, navControllerUser);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        appUser = (AppUser)intent.getSerializableExtra("AppUser");
        if (appUser != null) {
            Log.d(TAG, "Get appUser in User Main Activity as" + appUser.toString());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navControllerUser.navigateUp();
    }
}
