package com.flagcamp.donationcollector.ui.both;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.ActivityPasswordsigninBinding;
import com.flagcamp.donationcollector.databinding.FragmentChangePasswordBinding;
import com.flagcamp.donationcollector.databinding.FragmentProfileBinding;
import com.flagcamp.donationcollector.signin.AppUser;
import com.flagcamp.donationcollector.signin.PasswordSignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordFragment extends Fragment
        implements View.OnClickListener {

    private FragmentChangePasswordBinding binding;
    private static final String TAG = "ChangePasswordFragment";
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private AppUser appUser;

    public ChangePasswordFragment() {

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
        user = mAuth.getCurrentUser();
        // retrieve data from database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        binding = FragmentChangePasswordBinding.inflate(getLayoutInflater());

        binding.changePasswordConfirmButton.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void changePassWord() {
        Log.d(TAG, "onClick change password button");
        // now we need to first get the new password from the input text box
        // check two passwords match and update the user's information
        String newPassword = binding.fieldPassword.getText().toString();
        String confirmPassword = binding.fieldConfirmPassword.getText().toString();
        if (!validatePassword(newPassword, confirmPassword)) {
            return;
        }
        Log.d(TAG, "Password valid");
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                            // if success change the password, we need to end this session and restart the login
                            signOut();
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut();
        Intent intent = new Intent(getActivity(), PasswordSignInActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private boolean validatePassword(String password, String confirmPassword) {
        boolean valid = true;
        if (password.length() < 6) {
            binding.fieldPassword.setError("Length of password must longer than 6.");
            valid = false;
        } else {
            binding.fieldPassword.setError(null);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            binding.fieldConfirmPassword.setError("Required.");
            valid = false;
        } else {
            binding.fieldConfirmPassword.setError(null);
        }
        if (!password.equals(confirmPassword)) {
            binding.fieldConfirmPassword.setError("Password does not match.");
            valid = false;
        } else {
            binding.fieldConfirmPassword.setError(null);
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_password_confirm_button:
                changePassWord();
                break;
        }
    }
}
