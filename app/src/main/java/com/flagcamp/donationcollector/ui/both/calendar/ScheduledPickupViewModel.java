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

    public LiveData<List<Item>> getDateEquals(String date) {
        return repository.getDateEquals(date);
    }
}
