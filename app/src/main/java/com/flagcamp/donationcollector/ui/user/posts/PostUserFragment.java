package com.flagcamp.donationcollector.ui.user.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentPostUserBinding;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.model.Item;

import java.util.List;

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

        PostRepository repository = new PostRepository(getContext());
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository)).get(PostUserViewModel.class);

        /**
         * Currently the posterId is hardcoded to 0, later it should be obtained from the AppUser object
         */
        viewModel.getUserPosts("0").observe(getViewLifecycleOwner(), postResponse -> {
            if(postResponse != null) {
                Log.d("PostUserFragment", postResponse.toString());
            } else {
                Log.d("PostUserFragment", "Null postResponse");
            }
        });
    }
}
