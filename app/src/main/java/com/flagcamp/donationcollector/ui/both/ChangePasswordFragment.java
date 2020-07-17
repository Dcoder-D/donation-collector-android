package com.flagcamp.donationcollector.ui.both;

import android.os.Bundle;
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
import com.flagcamp.donationcollector.databinding.FragmentChangePasswordBinding;
import com.flagcamp.donationcollector.databinding.FragmentProfileBinding;
import com.flagcamp.donationcollector.signin.AppUser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordFragment extends Fragment
        implements View.OnClickListener {

    private FragmentChangePasswordBinding binding;
    private static final String TAG = "ChangePasswordFragment";
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
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
        // retrieve data from database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        binding = FragmentChangePasswordBinding.inflate(getLayoutInflater());

        binding.changePasswordConfirmButton.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void changePassWord() {
        Log.d(TAG, "onClick change password button");
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
