package com.flagcamp.donationcollector.ui.user.posts;

import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.repository.PostRepository;


public class PostDetailsUserViewModel extends ViewModel {
    private final PostRepository repository;

    public PostDetailsUserViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public Boolean deletePost(String itemId) {
        return repository.deletePost(itemId);
    }
}