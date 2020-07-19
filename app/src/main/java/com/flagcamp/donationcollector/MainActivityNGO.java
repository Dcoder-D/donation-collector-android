package com.flagcamp.donationcollector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.flagcamp.donationcollector.signin.AppUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityNGO extends AppCompatActivity {

    private NavController navControllerNGO;
    private AppUser appUser;
    private static final String TAG = "MainActivityNGO";


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        appUser = (AppUser)intent.getSerializableExtra("AppUser");
        if (appUser != null) {
            Log.d(TAG, "Get appUser in NGO Main Activity as" + appUser.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ngo);
//        setContentView(R.layout.fragment_login);

        BottomNavigationView navViewNGO = findViewById(R.id.nav_view_ngo);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager()
                                        .findFragmentById(R.id.nav_host_ngo_fragment);
        navControllerNGO = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navViewNGO, navControllerNGO);
//        NavigationUI.setupActionBarWithNavController(this, navControllerNGO);


    }

    @Override
    public boolean onSupportNavigateUp() {
        return navControllerNGO.navigateUp();
    }
}