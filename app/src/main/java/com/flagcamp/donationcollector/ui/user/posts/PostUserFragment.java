package com.flagcamp.donationcollector.ui.user.posts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentPostUserBinding;

import static android.app.Activity.RESULT_OK;

public class PostUserFragment extends Fragment {

    private FragmentPostUserBinding binding;
    ImageView addIcon;

    public PostUserFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentPostUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.postUserAddIcon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Navigation.findNavController(v).navigate(R.id.action_nav_post_user_to_nav_albums);
           }
        });
    }

}
