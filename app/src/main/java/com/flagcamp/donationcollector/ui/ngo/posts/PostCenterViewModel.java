package com.flagcamp.donationcollector.ui.ngo.posts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;

import java.util.List;

public class PostCenterViewModel extends ViewModel {

    private PostRepository repository;

    public PostCenterViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Item>> getStatusEquals(String status) {
        return repository.getStatusEquals(status);
    }

    public LiveData<List<Item>> getPostsByLocation(String location, String distance) {
        return repository.getPostsByLocation(location, distance);
    }
}
