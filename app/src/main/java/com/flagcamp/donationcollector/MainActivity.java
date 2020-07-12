package com.flagcamp.donationcollector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navControllerNGO;

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