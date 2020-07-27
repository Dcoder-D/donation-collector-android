package com.flagcamp.donationcollector.ui.ngo.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;

import java.util.List;

public class NGOScheduledPickupViewModel extends ViewModel {

    private PostRepository repository;

    public NGOScheduledPickupViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Item>> getNGODateEquals(String date, String NGOId) {
        return repository.getNGODateEquals(date, NGOId);
    }
}
