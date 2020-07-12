package com.flagcamp.donationcollector.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.flagcamp.donationcollector.main.NgoActivity;
import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.ActivityPasswordsigninBinding;
import com.flagcamp.donationcollector.main.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PasswordSignInActivity extends BaseActivity implements
        View.OnClickListener {

    private static final String TAG = "PasswordSignInActivity";
    private static final int RESULT_NEEDS_MFA_SIGN_IN = 42;

    private ActivityPasswordsigninBinding mBinding;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private boolean isUser;
    private boolean isRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPasswordsigninBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setProgressBar(mBinding.progressBar);

        // Buttons
        mBinding.signInButton.setOnClickListener(this);
        mBinding.createAccountButton.setOnClickListener(this);
        mBinding.radioNgo.setOnClickListener(this);
        mBinding.radioUser.setOnClickListener(this);
        mBinding.switchToLoginButton.setOnClickListener(this);
        mBinding.switchToRegisterButton.setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        // [END initialize_database_ref]

        isUser = ((RadioButton) findViewById(R.id.radio_user)).isChecked();

        // initialized the page to be the login page
        isRegister = false;
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressBar();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressBar();
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            onAuthSuccess(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Snackbar.make(mBinding.passwordLoginLayout,
                                    "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void onAuthSuccess(FirebaseUser user) {
        AppUser appUser = new AppUser(
                mBinding.fieldEmail.getText().toString(),
                mBinding.fieldPhone.getText().toString(),
                isUser);
        if (isUser) {
            appUser.setFirstName(mBinding.fieldFirstName.getText().toString());
            appUser.setLastName(mBinding.fieldLastName.getText().toString());
        } else {
            appUser.setOrganizationName(mBinding.fieldOrganizationName.getText().toString());
        }
        writeNewUser(user.getUid(), appUser);
        registerToLogin();
    }

    private void writeNewUser(String userId, AppUser appUser) {
        mDatabase.child(userId).setValue(appUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Snackbar.make(mBinding.coordinatorLayout,
                            "Registration Success.", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(mBinding.coordinatorLayout,
                            "Registration Failed.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void updateUI(FirebaseUser user) {
        hideProgressBar();
        if (user != null) {
            // try to start the main activity here
            // and finish the login activity
            Class thisClass = isUser ? UserActivity.class : NgoActivity.class;
            Intent intent = new Intent(PasswordSignInActivity.this, thisClass);
            startActivity(intent);
            finish();
        } else {
            mBinding.emailPasswordFields.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mBinding.fieldEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mBinding.fieldEmail.setError("Required.");
            valid = false;
        } else {
            mBinding.fieldEmail.setError(null);
        }

        String password = mBinding.fieldPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mBinding.fieldPassword.setError("Required.");
            valid = false;
        } else {
            mBinding.fieldPassword.setError(null);
        }

        if (isRegister) {
            String confirmPassword = mBinding.fieldConfirmPassword.getText().toString();
            if (TextUtils.isEmpty(confirmPassword)) {
                mBinding.fieldPassword.setError("Required.");
                valid = false;
            } else if (!confirmPassword.equals(password)) {
                mBinding.fieldConfirmPassword.setError("Password does not match.");
                valid = false;
            } else {
                mBinding.fieldConfirmPassword.setError(null);
            }

            if (isUser) {
                String firstName = mBinding.fieldFirstName.getText().toString();
                if (TextUtils.isEmpty(firstName)) {
                    mBinding.fieldFirstName.setError("Required.");
                    valid = false;
                } else {
                    mBinding.fieldFirstName.setError(null);
                }

                String lastName = mBinding.fieldLastName.getText().toString();
                if (TextUtils.isEmpty(lastName)) {
                    mBinding.fieldLastName.setError("Required.");
                    valid = false;
                } else {
                    mBinding.fieldLastName.setError(null);
                }
            } else {
                String organizationName = mBinding.fieldOrganizationName.getText().toString();
                if (TextUtils.isEmpty(organizationName)) {
                    mBinding.fieldOrganizationName.setError("Required.");
                    valid = false;
                } else {
                    mBinding.fieldOrganizationName.setError(null);
                }
            }

            String phone = mBinding.fieldPhone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                mBinding.fieldPhone.setError("Required.");
                valid = false;
            } else {
                mBinding.fieldPhone.setError(null);
            }
        }

        return valid;
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressBar();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            validateUserType(user);
//                            if (validateUserType(user)) {
//                                updateUI(user);
//                            } else {
//                                Snackbar.make(mBinding.coordinatorLayout, "Log in failed due to wrong type of user.",
//                                        Snackbar.LENGTH_SHORT).show();
//                                // Firebase sign out
//                                mAuth.signOut();
//                                updateUI(null);
//                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Snackbar.make(mBinding.passwordLoginLayout, "Authentication failed.",
                                    Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                            // [START_EXCLUDE]
                            checkForMultiFactorFailure(task.getException());
                            // [END_EXCLUDE]
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
//                            mBinding.status.setText(R.string.auth_failed);
                            Log.d(TAG, "Authentication Failed");
                        }
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void validateUserType(final FirebaseUser user) {
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppUser appUser = dataSnapshot.getValue(AppUser.class);
                if (appUser.isUser() == isUser) {
//                    loginTypeMatch = true;
                    updateUI(user);
                } else {
//                    loginTypeMatch = false;
                    Snackbar.make(mBinding.coordinatorLayout, "Log in failed due to wrong type of user.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    // Firebase sign out
                    mAuth.signOut();
                    updateUI(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child(user.getUid()).addValueEventListener(userListener);
    }

    private void checkForMultiFactorFailure(Exception e) {
        if (e instanceof FirebaseAuthMultiFactorException) {
            Log.w(TAG, "multiFactorFailure", e);
            Intent intent = new Intent();
            MultiFactorResolver resolver = ((FirebaseAuthMultiFactorException) e).getResolver();
            intent.putExtra("EXTRA_MFA_RESOLVER", resolver);
//            setResult(MultiFactorActivity.RESULT_NEEDS_MFA_SIGN_IN, intent);
            setResult(RESULT_NEEDS_MFA_SIGN_IN, intent);
            finish();
        }
    }

    private void registerToLogin() {
        hideProgressBar();
        mBinding.fieldConfirmPassword.setVisibility(View.GONE);
        mBinding.fieldFirstName.setVisibility(View.GONE);
        mBinding.fieldLastName.setVisibility(View.GONE);
        mBinding.fieldOrganizationName.setVisibility(View.GONE);
        mBinding.fieldPhone.setVisibility(View.GONE);
        mBinding.switchFromRegisterToLogin.setVisibility(View.GONE);
        mBinding.switchFromLoginToRegister.setVisibility(View.VISIBLE);
        mBinding.signInButton.setVisibility(View.VISIBLE);
        mBinding.createAccountButton.setVisibility(View.GONE);
    }

    private void loginToRegister() {
        hideProgressBar();
        mBinding.fieldConfirmPassword.setVisibility(View.VISIBLE);
        if (isUser) {
            mBinding.fieldFirstName.setVisibility(View.VISIBLE);
            mBinding.fieldLastName.setVisibility(View.VISIBLE);
            mBinding.fieldOrganizationName.setVisibility(View.GONE);
        } else {
            mBinding.fieldFirstName.setVisibility(View.GONE);
            mBinding.fieldLastName.setVisibility(View.GONE);
            mBinding.fieldOrganizationName.setVisibility(View.VISIBLE);
        }
        mBinding.fieldPhone.setVisibility(View.VISIBLE);
        mBinding.switchFromRegisterToLogin.setVisibility(View.VISIBLE);
        mBinding.switchFromLoginToRegister.setVisibility(View.GONE);
        mBinding.signInButton.setVisibility(View.GONE);
        mBinding.createAccountButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createAccountButton:
                createAccount(mBinding.fieldEmail.getText().toString(), mBinding.fieldPassword.getText().toString());
                break;
            case R.id.signInButton:
                signIn(mBinding.fieldEmail.getText().toString(), mBinding.fieldPassword.getText().toString());
                break;
            case R.id.radio_user:
                isUser = true;
                if (isRegister) {
                    loginToRegister();
                }
                break;
            case R.id.radio_ngo:
                isUser = false;
                if (isRegister) {
                    loginToRegister();
                }
                break;
            case R.id.switchToLoginButton:
                isRegister = false;
                registerToLogin();
                break;
            case R.id.switchToRegisterButton:
                isRegister = true;
                loginToRegister();
                break;
        }
    }
}