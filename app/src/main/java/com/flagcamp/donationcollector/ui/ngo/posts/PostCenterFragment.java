package com.flagcamp.donationcollector.ui.ngo.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flagcamp.donationcollector.databinding.FragmentPostCenterBinding;

public class PostCenterFragment extends Fragment {

    private PostCenterViewModel viewModel;
    private FragmentPostCenterBinding binding;
    ImageView mapIcon;
    Button placeHolder;

    public PostCenterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.fragment_)
        binding = FragmentPostCenterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapIcon = view.findViewById(R.id.post_center_map_icon);
        placeHolder = view.findViewById(R.id.post_center_placeholder);

        mapIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavHostFragment.findNavController(PostCenterFragment.this).navigate(R.id.action_title_postcenter_to_location_selector);

              PostCenterFragmentDirections.ActionTitlePostcenterToLocationSelector actionTitlePostcenterToLocationSelector =
              PostCenterFragmentDirections.actionTitlePostcenterToLocationSelector();
              actionTitlePostcenterToLocationSelector.setDummy("test");
              NavHostFragment.findNavController(PostCenterFragment.this).navigate(actionTitlePostcenterToLocationSelector);
            }
        });

        placeHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostCenterFragmentDirections.ActionTitlePostcenterToPostDetails actionTitlePostcenterToPostDetails =
                        PostCenterFragmentDirections.actionTitlePostcenterToPostDetails();
                actionTitlePostcenterToPostDetails.setDummy("test");
                NavHostFragment.findNavController(PostCenterFragment.this).navigate(actionTitlePostcenterToPostDetails);
            }
        });
    }
}
