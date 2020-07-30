package com.flagcamp.donationcollector.ui.ngo.posts;

import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.repository.PostRepository;


public class PostDetailsNGOViewModel extends ViewModel {
    private final PostRepository repository;

    public PostDetailsNGOViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public Boolean confirmPickUp(String itemId, String ngoId, String ngoName, String pickUpDate) {
        return repository.confirmPickUp(itemId, ngoId, ngoName, pickUpDate);
    }
}
