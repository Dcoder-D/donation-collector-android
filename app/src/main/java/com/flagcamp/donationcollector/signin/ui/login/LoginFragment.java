package com.flagcamp.donationcollector.signin.ui.login;

import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.flagcamp.donationcollector.R;

import com.flagcamp.donationcollector.databinding.FragmentLoginBinding;
import com.flagcamp.donationcollector.signin.repository.LoginRepository;
import com.flagcamp.donationcollector.signin.repository.LoginViewModelFactory;

public class LoginFragment extends BaseFragment
        implements View.OnClickListener {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private boolean isRegister;
    private boolean isChooseUser;
    
    public LoginFragment() {
        
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // now bind the buttons
        binding.signInButton.setOnClickListener(this);
        binding.createAccountButton.setOnClickListener(this);
        binding.radioNgo.setOnClickListener(this);
        binding.radioUser.setOnClickListener(this);
        binding.switchToLoginButton.setOnClickListener(this);
        binding.switchToRegisterButton.setOnClickListener(this);
        setProgressBar(binding.progressBar);
        isRegister = false;
        isChooseUser = ((RadioButton) view.findViewById(R.id.radio_user)).isChecked();

        // set viewModel
        LoginRepository repository = new LoginRepository(getContext());
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(repository, binding))
                .get(LoginViewModel.class);

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = binding.fieldEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            binding.fieldEmail.setError("Required.");
            valid = false;
        } else {
            binding.fieldEmail.setError(null);
        }

        String password = binding.fieldPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            binding.fieldPassword.setError("Required.");
            valid = false;
        } else {
            binding.fieldPassword.setError(null);
        }

        if (isRegister) {
            String confirmPassword = binding.fieldConfirmPassword.getText().toString();
            if (TextUtils.isEmpty(confirmPassword)) {
                binding.fieldPassword.setError("Required.");
                valid = false;
            } else if (!confirmPassword.equals(password)) {
                binding.fieldConfirmPassword.setError("Password does not match.");
                valid = false;
            } else {
                binding.fieldConfirmPassword.setError(null);
            }

            if (isChooseUser) {
                String firstName = binding.fieldFirstName.getText().toString();
                if (TextUtils.isEmpty(firstName)) {
                    binding.fieldFirstName.setError("Required.");
                    valid = false;
                } else {
                    binding.fieldFirstName.setError(null);
                }

                String lastName = binding.fieldLastName.getText().toString();
                if (TextUtils.isEmpty(lastName)) {
                    binding.fieldLastName.setError("Required.");
                    valid = false;
                } else {
                    binding.fieldLastName.setError(null);
                }
            } else {
                String organizationName = binding.fieldOrganizationName.getText().toString();
                if (TextUtils.isEmpty(organizationName)) {
                    binding.fieldOrganizationName.setError("Required.");
                    valid = false;
                } else {
                    binding.fieldOrganizationName.setError(null);
                }
            }

            String phone = binding.fieldPhone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                binding.fieldPhone.setError("Required.");
                valid = false;
            } else {
                binding.fieldPhone.setError(null);
            }
        }

        return valid;
    }

    private void registerToLogin() {
        hideProgressBar();
        binding.fieldConfirmPassword.setVisibility(View.GONE);
        binding.fieldFirstName.setVisibility(View.GONE);
        binding.fieldLastName.setVisibility(View.GONE);
        binding.fieldOrganizationName.setVisibility(View.GONE);
        binding.fieldPhone.setVisibility(View.GONE);
        binding.switchFromRegisterToLogin.setVisibility(View.GONE);
        binding.switchFromLoginToRegister.setVisibility(View.VISIBLE);
        binding.signInButton.setVisibility(View.VISIBLE);
        binding.createAccountButton.setVisibility(View.GONE);
    }

    private void loginToRegister() {
        hideProgressBar();
        binding.fieldConfirmPassword.setVisibility(View.VISIBLE);
        if (isChooseUser) {
            binding.fieldFirstName.setVisibility(View.VISIBLE);
            binding.fieldLastName.setVisibility(View.VISIBLE);
            binding.fieldOrganizationName.setVisibility(View.GONE);
        } else {
            binding.fieldFirstName.setVisibility(View.GONE);
            binding.fieldLastName.setVisibility(View.GONE);
            binding.fieldOrganizationName.setVisibility(View.VISIBLE);
        }
        binding.fieldPhone.setVisibility(View.VISIBLE);
        binding.switchFromRegisterToLogin.setVisibility(View.VISIBLE);
        binding.switchFromLoginToRegister.setVisibility(View.GONE);
        binding.signInButton.setVisibility(View.GONE);
        binding.createAccountButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createAccountButton:
                if (validateForm()) {
                    loginViewModel.createAccount(binding.fieldEmail.getText().toString(),
                            binding.fieldPassword.getText().toString(), isChooseUser);
                }
                break;
            case R.id.signInButton:
                loginViewModel.login(binding.fieldEmail.getText().toString(),
                        binding.fieldPassword.getText().toString(), isChooseUser);
                break;
            case R.id.radio_user:
                isChooseUser = true;
                if (isRegister) {
                    loginToRegister();
                }
                break;
            case R.id.radio_ngo:
                isChooseUser = false;
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