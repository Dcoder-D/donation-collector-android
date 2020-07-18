package com.flagcamp.donationcollector.ui.user.posts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;

import java.util.List;

public class PostUserViewModel extends ViewModel {

    private final PostRepository repository;

    public PostUserViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Item>> getUserPosts(String posterId) {
        return repository.getUserPosts(posterId);
    }
}
