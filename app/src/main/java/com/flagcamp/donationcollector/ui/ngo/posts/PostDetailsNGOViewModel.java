package com.flagcamp.donationcollector.ui.ngo.posts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.repository.PostRepository;

import retrofit2.Response;


public class PostDetailsNGOViewModel extends ViewModel {
    private final PostRepository repository;

    public PostDetailsNGOViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public LiveData<Response> schedulePickUp(String itemId, String ngoId, String ngoName, String pickUpDate) {
        return repository.schedulePickUp(itemId, ngoId, ngoName, pickUpDate);
    }
    public LiveData<Response> confirmPickUp(String itemId, String ngoId) {
        return repository.confirmPickUp(itemId, ngoId);
    }
}
