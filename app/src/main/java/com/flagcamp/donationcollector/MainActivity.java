package com.flagcamp.donationcollector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.flagcamp.donationcollector.databinding.ActivityGooglesigninBinding;
import com.flagcamp.donationcollector.databinding.ActivityMainBinding;
import com.flagcamp.donationcollector.signin.ChooserActivity;
import com.flagcamp.donationcollector.signin.GoogleSignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    private Button mBtLaunchSignIn;
    private static final String TAG = "MainActivity";
    private ActivityMainBinding mBinding;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
//        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        // Button listeners
        mBinding.signOutButton.setOnClickListener(this);
        mBinding.disconnectButton.setOnClickListener(this);

        mBtLaunchSignIn = findViewById(R.id.bt_launch_signin);
        mBtLaunchSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Success in the Main Activity");
            }
        });
        // show the login success message
        Snackbar.make(mBinding.mainLayout, "Login Success.", Snackbar.LENGTH_SHORT).show();
    }

    // [START signOut]
    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();
        // Google revoke access
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    private void updateUI() {
        Intent intent = new Intent(MainActivity.this, ChooserActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
        }
    }
}