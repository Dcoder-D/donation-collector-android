package com.flagcamp.donationcollector.ui.ngo.posts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flagcamp.donationcollector.databinding.FragmentPostCenterBinding;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;

public class PostCenterFragment extends Fragment {

    private PostCenterViewModel viewModel;
    private static FragmentPostCenterBinding binding;
    ImageView mapIcon;
    private String location;
    private String distance;


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

        PostRepository repository = new PostRepository(getContext());

//        repository.getAllLocation().observe(getViewLifecycleOwner(), response -> {
//            location = response.get(0).location;
//            if(location == null) {
//                location = "235 Grand St, Jersey City, NJ 07302";
//            }
//            distance = response.get(0).distance;
//            if(distance == null) {
//                distance = "100";
//            }
//        });

        location = PostCenterFragmentArgs.fromBundle(getArguments()).getLocation();
        if(location == null) {
            location = "235 Grand St, Jersey City, NJ 07302";
        }
        distance = PostCenterFragmentArgs.fromBundle(getArguments()).getDistance();
        if(distance == null) {
            distance = "100";
        }

        mapIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavHostFragment.findNavController(PostCenterFragment.this).navigate(R.id.action_title_postcenter_to_location_selector);

              PostCenterFragmentDirections.ActionTitlePostcenterToLocationSelector actionTitlePostcenterToLocationSelector =
              PostCenterFragmentDirections.actionTitlePostcenterToLocationSelector("PostCenterFragment");

              NavHostFragment.findNavController(PostCenterFragment.this).navigate(actionTitlePostcenterToLocationSelector);
            }
        });

        PostCenterAdapter postCenterAdapter = new PostCenterAdapter(this, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.postRecyclerView.setLayoutManager(linearLayoutManager);
        binding.postRecyclerView.setAdapter(postCenterAdapter);





        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository)).get(PostCenterViewModel.class);


        viewModel.getPostsByLocation(location, distance).observe(getViewLifecycleOwner(), postResponse -> {
            if(postResponse != null) {
                Log.d("PostCenterFragment", postResponse.toString());
                postCenterAdapter.setItems(postResponse);
            } else {
                Log.d("PostCenterFragment", "Null postResponse");
            }
        });

//        placeHolder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PostCenterFragmentDirections.ActionTitlePostcenterToPostDetails actionTitlePostcenterToPostDetails =
//                        PostCenterFragmentDirections.actionTitlePostcenterToPostDetails();
//                actionTitlePostcenterToPostDetails.setDummy("test");
//                NavHostFragment.findNavController(PostCenterFragment.this).navigate(actionTitlePostcenterToPostDetails);
//            }
//        });


    }

//    private static class PostClickListener implements View.OnClickListener {
//        private final Context context;
//
//        private PostClickListener(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        public void onClick(View v) {
//            int selectedItemPosition = binding.postRecyclerView.getChildAdapterPosition(v);
//            RecyclerView.ViewHolder viewHolder = binding.postRecyclerView.findViewHolderForAdapterPosition(selectedItemPosition);
//            PostCenterFragmentDirections.ActionTitlePostcenterToPostDetails actionTitlePostcenterToPostDetails =
//                    PostCenterFragmentDirections.actionTitlePostcenterToPostDetails();
//
//        }
//    }
}
