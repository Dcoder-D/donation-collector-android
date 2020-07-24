package com.flagcamp.donationcollector.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.flagcamp.donationcollector.ui.ngo.posts.PostCenterViewModel;
import com.flagcamp.donationcollector.ui.user.posts.PostDetailsUserViewModel;
import com.flagcamp.donationcollector.ui.user.posts.PostUserViewModel;
import com.flagcamp.donationcollector.ui.user.posts.PostsPreviewViewModel;

public class PostViewModelFactory implements ViewModelProvider.Factory {

    private final PostRepository repository;

    public PostViewModelFactory(PostRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PostUserViewModel.class)) {
            return (T) new PostUserViewModel(repository);
        } else if(modelClass.isAssignableFrom(PostCenterViewModel.class)) {
            return (T) new PostCenterViewModel(repository);
        } else if(modelClass.isAssignableFrom(PostDetailsUserViewModel.class)) {
            return (T) new PostDetailsUserViewModel(repository);
        } else if(modelClass.isAssignableFrom(PostsPreviewViewModel.class)) {
            return (T) new PostsPreviewViewModel(repository);
        } else {
            throw new IllegalStateException("Unkown ViewModel");
        }
    }
}
