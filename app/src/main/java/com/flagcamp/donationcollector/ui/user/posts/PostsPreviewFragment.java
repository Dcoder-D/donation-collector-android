package com.flagcamp.donationcollector.ui.user.posts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentPostsPreviewBinding;

public class PostsPreviewFragment extends Fragment {

    FragmentPostsPreviewBinding binding;

    public PostsPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostsPreviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}