package com.flagcamp.donationcollector;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityUser extends AppCompatActivity {
    private NavController navControllerUser;

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
    public boolean onSupportNavigateUp() {
        return navControllerUser.navigateUp();
    }
}
