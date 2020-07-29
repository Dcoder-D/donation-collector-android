package com.flagcamp.donationcollector.ui.user.posts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentPostUserBinding;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.SignInRepository;
import com.flagcamp.donationcollector.signin.AppUser;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PostUserFragment extends Fragment {

    private FragmentPostUserBinding binding;
    private PostUserViewModel viewModel;
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
        addIcon = view.findViewById(R.id.post_user_add_icon);

        PostUserAdapter postUserAdapter = new PostUserAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.postRecyclerView.setLayoutManager(linearLayoutManager);
        binding.postRecyclerView.setAdapter(postUserAdapter);

        postUserAdapter.setOnItemClickListener(new PostUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                PostDetailsUserFragment.setItem(item);
                Navigation.findNavController(view).navigate(R.id.action_title_postuser_to_post_details);
            }
        });

        PostRepository repository = new PostRepository(getContext());
        SignInRepository signInRepository = new SignInRepository();
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository, signInRepository)).get(PostUserViewModel.class);

        AppUser[] appUsers = new AppUser[1];

        viewModel.getAppUser().observe(getViewLifecycleOwner(), response -> {
            if(response != null) {
                Log.d("PostUserFragment", "AppUser is: " + response.toString());
                appUsers[0] = response.get(0);

                viewModel.getUserPosts(appUsers[0].getUid()).observe(getViewLifecycleOwner(), postResponse -> {
                    if(postResponse != null) {
                        Log.d("PostUserFragment", postResponse.toString());
                        postUserAdapter.setItems(postResponse);
                    } else {
                        Log.d("PostUserFragment", "Null postResponse");
                    }
                });

                Log.d("PostUserFragment", "appUsers[0] is: " + appUsers[0].toString());
            } else {
                Log.d("PostUserFragment", "AppUser is: null");
            }
        });

        /**
         * Currently the posterId is hardcoded to 0, later it should be obtained from the AppUser object
         */
//        Log.d("PostUserFragment", "appUsers[0] is: " + appUsers[0]);
//
//        if(appUsers[0] != null) {
//            Log.d("PostUserFragment", "appUser is: " + appUsers[0].toString());
//            viewModel.getUserPosts(appUsers[0].getUid()).observe(getViewLifecycleOwner(), postResponse -> {
//                if(postResponse != null) {
//                    Log.d("PostUserFragment", postResponse.toString());
//                    postUserAdapter.setItems(postResponse);
//                } else {
//                    Log.d("PostUserFragment", "Null postResponse");
//                }
//            });
//        }


        binding.postUserAddIcon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Navigation.findNavController(v).navigate(R.id.action_nav_post_user_to_nav_albums);
           }

        });
    }

}
