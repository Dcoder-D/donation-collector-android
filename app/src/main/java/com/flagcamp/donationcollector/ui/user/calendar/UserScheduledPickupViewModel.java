package com.flagcamp.donationcollector.ui.user.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;

import java.util.List;

public class UserScheduledPickupViewModel extends ViewModel {

    private PostRepository repository;

    public UserScheduledPickupViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Item>> getUserDateEquals(String date, String posterId) {
        return repository.getUserDateEquals(date, posterId);
    }

    public LiveData<List<Item>> getUserPosts(String posterId) {
        return repository.getUserPosts(posterId);
    }
}
