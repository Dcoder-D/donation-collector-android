package com.flagcamp.donationcollector.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.flagcamp.donationcollector.ui.ngo.calendar.NGOScheduledPickupViewModel;
import com.flagcamp.donationcollector.ui.ngo.posts.PostCenterViewModel;
import com.flagcamp.donationcollector.ui.ngo.posts.PostDetailsNGOViewModel;
import com.flagcamp.donationcollector.ui.user.calendar.UserScheduledPickupViewModel;
import com.flagcamp.donationcollector.ui.user.posts.PostDetailsUserViewModel;
import com.flagcamp.donationcollector.ui.user.posts.PostUserViewModel;
import com.flagcamp.donationcollector.ui.user.posts.PostsPreviewViewModel;

public class PostViewModelFactory implements ViewModelProvider.Factory {

    private final PostRepository repository;
    private final  SignInRepository signInRepository;

    public PostViewModelFactory(PostRepository repository) {
        this.repository = repository;
        this.signInRepository = null;
    }

    public PostViewModelFactory(PostRepository repository, SignInRepository signInRepository) {
        this.repository = repository;
        this.signInRepository = signInRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PostUserViewModel.class)) {
            return (T) new PostUserViewModel(repository, signInRepository);
        } else if(modelClass.isAssignableFrom(PostCenterViewModel.class)) {
            return (T) new PostCenterViewModel(repository);
        } else if(modelClass.isAssignableFrom(NGOScheduledPickupViewModel.class)) {
            return (T) new NGOScheduledPickupViewModel(repository);
        } else if (modelClass.isAssignableFrom(UserScheduledPickupViewModel.class)) {
            return (T) new UserScheduledPickupViewModel(repository);
        } else if(modelClass.isAssignableFrom(PostDetailsUserViewModel.class)) {
            return (T) new PostDetailsUserViewModel(repository);
        } else if(modelClass.isAssignableFrom(PostsPreviewViewModel.class)) {
            return (T) new PostsPreviewViewModel(repository);
        } else if(modelClass.isAssignableFrom(PostDetailsNGOViewModel.class)) {
            return (T) new PostDetailsNGOViewModel(repository);
        } else {
            throw new IllegalStateException("Unkown ViewModel");
        }
    }
}
