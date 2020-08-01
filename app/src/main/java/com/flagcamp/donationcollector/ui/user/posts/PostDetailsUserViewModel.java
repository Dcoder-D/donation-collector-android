package com.flagcamp.donationcollector.ui.user.posts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.repository.PostRepository;

import retrofit2.Response;


public class PostDetailsUserViewModel extends ViewModel {
    private final PostRepository repository;

    public PostDetailsUserViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public LiveData<Response> deletePost(String userId, String itemId) {
        return repository.deletePost(userId, itemId);

    }
}