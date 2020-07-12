package com.flagcamp.donationcollector.ui.ngo.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentPostDetailsNgoBinding;

public class PostDetailsNGOFragment extends Fragment {

    FragmentPostDetailsNgoBinding binding;
    Button backButton;
    Button confirmButtion;

    public PostDetailsNGOFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentPostDetailsNgoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = view.findViewById(R.id.post_details_ngo_back_button);
        confirmButtion = view.findViewById(R.id.post_details_ngo_schedule_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetailsNGOFragmentDirections.ActionTitlePostDetailsToPostcenter actionTitlePostDetailsToPostcenter =
                        PostDetailsNGOFragmentDirections.actionTitlePostDetailsToPostcenter();
                actionTitlePostDetailsToPostcenter.setDummy("test");
                NavHostFragment.findNavController(PostDetailsNGOFragment.this).navigate(actionTitlePostDetailsToPostcenter);
            }
        });

        confirmButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetailsNGOFragmentDirections.ActionTitlePostDetailsToPostcenter actionTitlePostDetailsToPostcenter =
                        PostDetailsNGOFragmentDirections.actionTitlePostDetailsToPostcenter();
                actionTitlePostDetailsToPostcenter.setDummy("test");
                NavHostFragment.findNavController(PostDetailsNGOFragment.this).navigate(actionTitlePostDetailsToPostcenter);
            }
        });
    }
}
