package com.flagcamp.donationcollector.signin.repository;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.flagcamp.donationcollector.databinding.FragmentLoginBinding;
import com.flagcamp.donationcollector.signin.repository.LoginRepository;
import com.flagcamp.donationcollector.signin.ui.login.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final LoginRepository repository;
    private final FragmentLoginBinding binding;

    public LoginViewModelFactory(LoginRepository repository, FragmentLoginBinding binding) {
        this.repository = repository;
        this.binding = binding;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(binding);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}