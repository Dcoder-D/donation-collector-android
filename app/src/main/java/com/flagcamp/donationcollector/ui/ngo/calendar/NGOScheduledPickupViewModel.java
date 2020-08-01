package com.flagcamp.donationcollector.ui.ngo.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.model.PostUtils;
import com.flagcamp.donationcollector.repository.PostRepository;

import java.util.List;

import okhttp3.ResponseBody;

public class NGOScheduledPickupViewModel extends ViewModel {

    private PostRepository repository;

    public NGOScheduledPickupViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Item>> getNGODateEquals(String date, String NGOId) {
        return repository.getNGODateEquals(date, NGOId);
    }

    public LiveData<List<Item>> getPostsByNGOId(String ngoId) {
        return repository.getUserPosts(ngoId);
    }

    public LiveData<List<Item>> getNGOPosts(String ngoId) {
        return repository.getNGOPosts(ngoId);
    }

    public LiveData<ResponseBody> createRoute(String ngoName, String startLocation, List<Item> scheduledItems) {
        return repository.createRoute(PostUtils.createRouteBody(ngoName, startLocation, scheduledItems));
    }
}
