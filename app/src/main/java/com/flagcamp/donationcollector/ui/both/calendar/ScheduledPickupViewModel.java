package com.flagcamp.donationcollector.ui.both.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;

import java.util.List;

public class ScheduledPickupViewModel extends ViewModel {

    private PostRepository repository;

    public ScheduledPickupViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Item>> getNGODateEquals(String date, String NGOId) {
        return repository.getNGODateEquals(date, NGOId);
    }

    public LiveData<List<Item>> getUserDateEquals(String date, String posterId) {
        return repository.getUserDateEquals(date, posterId);
    }
}
