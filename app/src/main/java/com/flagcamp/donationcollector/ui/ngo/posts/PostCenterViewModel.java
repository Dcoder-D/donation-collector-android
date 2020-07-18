package com.flagcamp.donationcollector.ui.ngo.posts;

import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.repository.PostRepository;

public class PostCenterViewModel extends ViewModel {

    private PostRepository repository;

    public PostCenterViewModel(PostRepository repository) {
        this.repository = repository;
    }
}
