package com.flagcamp.donationcollector.signin.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import com.flagcamp.donationcollector.databinding.FragmentLoginBinding;
import com.flagcamp.donationcollector.signin.repository.LoginRepository;
import com.flagcamp.donationcollector.signin.AppUser;
import com.flagcamp.donationcollector.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    
    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private static final String TAG = "LoginFragment";
    private final MutableLiveData<AppUser> appUser = new MutableLiveData<>();

    public LoginViewModel(FragmentLoginBinding binding) {
        this.binding = binding;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }
    
    public boolean isLoggedIn() {
        return user != null;
    }

    public void login(String email, String password, boolean isChooseUser) {
        Log.d(TAG, "loginAccount:" + email);
        
    }

    public void createAccount(String email, String password, boolean isChooseUser) {
        Log.d(TAG, "createAccount:" + email);

    }

//    private void onAuthSuccess(FirebaseUser user, boolean isChooseUser) {
//        AppUser appUser = new AppUser.AppUserBuilder()
//                .emailAddress(binding.fieldEmail.getText().toString())
//                .phone(binding.fieldPhone.getText().toString())
//                .isUser(isChooseUser)
//                .uid(user.getUid())
//                .build();
//        if (isChooseUser) {
//            appUser.setFirstName(binding.fieldFirstName.getText().toString());
//            appUser.setLastName(binding.fieldLastName.getText().toString());
//        } else {
//            appUser.setOrganizationName(binding.fieldOrganizationName.getText().toString());
//        }
//        writeNewUser(user.getUid(), appUser);
//        registerToLogin();
//    }


}