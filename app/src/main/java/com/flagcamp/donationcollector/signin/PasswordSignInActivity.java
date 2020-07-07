package com.flagcamp.donationcollector.signin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.flagcamp.donationcollector.MainActivity;
import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.ActivityPasswordsigninBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;

public class PasswordSignInActivity extends BaseActivity implements
        View.OnClickListener {

    private static final String TAG = "PasswordSignInActivity";
    private static final int RESULT_NEEDS_MFA_SIGN_IN = 42;

    private ActivityPasswordsigninBinding mBinding;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPasswordsigninBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setProgressBar(mBinding.progressBar);

        // Buttons
        mBinding.emailSignInButton.setOnClickListener(this);
        mBinding.emailCreateAccountButton.setOnClickListener(this);
        mBinding.signOutButton.setOnClickListener(this);
        mBinding.verifyEmailButton.setOnClickListener(this);
        mBinding.reloadButton.setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
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
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
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


    private void updateUI(FirebaseUser user) {
        hideProgressBar();
        if (user != null) {
            // try to start the main activity here
            // and finish the login activity
            Intent intent = new Intent(PasswordSignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            mBinding.status.setText(R.string.signed_out);
            mBinding.detail.setText(null);
            mBinding.emailPasswordButtons.setVisibility(View.VISIBLE);
            mBinding.emailPasswordFields.setVisibility(View.VISIBLE);
            mBinding.signedInButtons.setVisibility(View.GONE);
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
                            updateUI(user);
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
                            mBinding.status.setText(R.string.auth_failed);
                        }
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
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


    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        // Disable button
        mBinding.verifyEmailButton.setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        mBinding.verifyEmailButton.setEnabled(true);

                        if (task.isSuccessful()) {
                            Snackbar.make(mBinding.passwordLoginLayout,
                                    "Verification email sent to " + user.getEmail(),
                                    Snackbar.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Snackbar.make(mBinding.passwordLoginLayout,
                                    "Failed to send verification email.",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private void reload() {
        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateUI(mAuth.getCurrentUser());
                    Snackbar.make(mBinding.passwordLoginLayout,
                            "Reload successful!",
                            Snackbar.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "reload", task.getException());
                    Snackbar.make(mBinding.passwordLoginLayout,
                            "Failed to reload user.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emailCreateAccountButton:
                createAccount(mBinding.fieldEmail.getText().toString(), mBinding.fieldPassword.getText().toString());
                break;
            case R.id.emailSignInButton:
                signIn(mBinding.fieldEmail.getText().toString(), mBinding.fieldPassword.getText().toString());
                break;
            case R.id.signOutButton:
                signOut();
                break;
            case R.id.verifyEmailButton:
                sendEmailVerification();
                break;
            case R.id.reloadButton:
                reload();
                break;
        }
    }
}
