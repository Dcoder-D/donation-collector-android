package com.flagcamp.donationcollector.ui.both;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flagcamp.donationcollector.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
// 13405877593 闫凯
    private FragmentLoginBinding binding;
    Button loginButton;

    public LoginFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton = view.findViewById(R.id.login_button);
//        ActionOnlyNavDirections actionOnlyNavDirections = LoginFragmentDirections.actionTitleLoginToPostCenter();
//        LoginFragmentDirections.ActionTitleLoginToPostCenter actionTitleLoginToPostCenter = LoginFragmentDirections.actionTitleLoginToPostCenter();


//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_title_login_to_post_center);
//            }
//        });


    }
}
