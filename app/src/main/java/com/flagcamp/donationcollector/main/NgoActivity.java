package com.flagcamp.donationcollector.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.ActivityNgoBinding;
import com.flagcamp.donationcollector.databinding.ActivityUserBinding;
import com.flagcamp.donationcollector.signin.AppUser;
import com.flagcamp.donationcollector.signin.ChooserActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NgoActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "NgoActivity";
    private ActivityNgoBinding mBinding;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private AppUser appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityNgoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        mBinding.signOutButton.setOnClickListener(this);
        mBinding.disconnectButton.setOnClickListener(this);

        // show the login success message
        Snackbar.make(mBinding.ngoLayout, "Login Success.", Snackbar.LENGTH_SHORT).show();

        // retrieve data from database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        getUser(currentUser);
    }

    private void getUser(FirebaseUser user) {
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appUser = dataSnapshot.getValue(AppUser.class);
                TextView displayTextView = findViewById(R.id.user_info);
                String displayText = "Hello"
                        + "\n"
                        + appUser.getFirstName()
                        + "\n"
                        + appUser.getLastName()
                        + "\n"
                        + "Your are logged in as an Non-Governmental Organization";
                displayTextView.setText(displayText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child(user.getUid()).addValueEventListener(userListener);
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
        Intent intent = new Intent(NgoActivity.this, ChooserActivity.class);
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
