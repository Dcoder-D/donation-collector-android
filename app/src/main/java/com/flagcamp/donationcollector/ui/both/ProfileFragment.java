package com.flagcamp.donationcollector.ui.both;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentProfileBinding;
import com.flagcamp.donationcollector.repository.SignInRepository;
import com.flagcamp.donationcollector.signin.AppUser;
import com.flagcamp.donationcollector.signin.PasswordSignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileFragment extends Fragment
        implements View.OnClickListener {

    private FragmentProfileBinding binding;
    private static final String TAG = "ProfileFragment";
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;
    private AppUser appUser;



    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mAuth = FirebaseAuth.getInstance();
        // retrieve data from database
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        binding.logoutButton.setOnClickListener(this);
        binding.changePassword.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        appUser = (AppUser) getActivity().getIntent().getSerializableExtra("AppUser");
        if (appUser != null) {
            Log.d(TAG, "Get appUser in Profile Fragment as" + appUser.toString());
            if (appUser.isUser()){
                binding.fullName.setText(appUser.getFirstName() + " " + appUser.getLastName());
            } else {
                binding.fullName.setText(appUser.getOrganizationName());
            }
        }

        binding.email.setText(appUser.getEmailAddress());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // [START signOut]
    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        mGoogleSignInClient.signOut();
        // now we need to write appUser to the Room database
        SignInRepository repository = new SignInRepository();
        repository.deleteAppUser(appUser);
        Intent intent = new Intent(getActivity(), PasswordSignInActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                signOut();
                break;
            case R.id.change_password:
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_title_profile_to_change_password);
                break;
        }
    }
    // [END signOut]
}
