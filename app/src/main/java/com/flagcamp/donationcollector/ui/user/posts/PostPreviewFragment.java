package com.flagcamp.donationcollector.ui.user.posts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentPostPreviewBinding;

public class PostPreviewFragment extends Fragment {
    FragmentPostPreviewBinding binding;
    Button backButton;
    Button postButton;
    TextView addToPost;
    TextView addAnotherPost;
    TextView locationText;
    TextView selectedLocation;
    ImageView imagePlaceHolder;
    String imagePath;
    String location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentPostPreviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = binding.albumsBackButton;
        postButton = binding.postButton;
        addToPost = binding.addToPostButton;
        addAnotherPost = binding.addAnotherButton;
        imagePlaceHolder = binding.imagePlaceholder;
        locationText = binding.locationText;
        selectedLocation = binding.locationDisplayText;

        imagePath = PostPreviewFragmentArgs.fromBundle(getArguments()).getImagePath();
        location = PostPreviewFragmentArgs.fromBundle(getArguments()).getLocation();

        if(location != null) {
            selectedLocation.setText(location);
        }

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imagePlaceHolder.setImageBitmap(bitmap);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(PostPreviewFragment.this).navigate(R.id.action_title_post_preview_to_albums);
            }
        });

        addAnotherPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(PostPreviewFragment.this).navigate(R.id.action_title_post_preview_to_albums);
            }
        });

        locationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostPreviewFragmentDirections.ActionTitlePostPreviewToLocation actionTitlePostPreviewToLocation
                        = PostPreviewFragmentDirections.actionTitlePostPreviewToLocation("PostPreviewFragment");
                NavHostFragment.findNavController(PostPreviewFragment.this).navigate(actionTitlePostPreviewToLocation);
            }
        });
    }
}
