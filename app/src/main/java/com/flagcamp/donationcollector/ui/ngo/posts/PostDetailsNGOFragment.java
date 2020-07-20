package com.flagcamp.donationcollector.ui.ngo.posts;

import android.os.Bundle;
import android.view.Gravity;
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
import com.flagcamp.donationcollector.model.Item;
import com.squareup.picasso.Picasso;

public class PostDetailsNGOFragment extends Fragment {

    FragmentPostDetailsNgoBinding binding;
    Button backButton;
    Button confirmButtion;
    private static Item mitem;

    public PostDetailsNGOFragment() {

    }
    public static void setItem(Item item) {
        mitem = item;
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
        backButton = binding.postDetailsNgoBackButton;
        confirmButtion = binding.postDetailsNgoScheduleButton;

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


        mitem = PostDetailsNGOFragmentArgs.fromBundle(getArguments()).getPost();

        if(mitem != null) {
            Picasso.get().load(mitem.urlToImage).into(binding.postDetailsUserImg);
            binding.category.setText(mitem.category);
            binding.size.setText("Size: " + mitem.size);
            binding.address.setText(mitem.location);
        }else {
            throw new IllegalArgumentException("has no info to show details");
        }
    }
}
