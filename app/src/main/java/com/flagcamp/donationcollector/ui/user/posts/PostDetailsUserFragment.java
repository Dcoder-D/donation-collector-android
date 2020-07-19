package com.flagcamp.donationcollector.ui.user.posts;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.flagcamp.donationcollector.R;

public class PostDetailsUserFragment extends Fragment {

    public static PostDetailsUserFragment newInstance() {
        return new PostDetailsUserFragment();
    }

    private PostDetailsUserViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_details_user_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostDetailsUserViewModel.class);
        // TODO: Use the ViewModel
    }

}