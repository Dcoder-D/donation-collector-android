package com.flagcamp.donationcollector.ui.user.posts;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;

import java.util.List;

public class PostsPreviewViewModel extends ViewModel {
    // need a viewModel to do following things:
    // 1. add item to room database to display added items
    // 2. when doing a post, change all items status from added to pending
    // 3. when doing a post, send HTTP request to backend service
    private PostRepository repository;

    public PostsPreviewViewModel(PostRepository repository) {
        this.repository = repository;
    }

    public void addItemAsAdded(Item item) {
        repository.addItemAsAdded(item);
    }

    public LiveData<List<Item>> getAddedItems() {
        return repository.getAddedItems();
    }

    public void postAllAddedItem() {
        repository.postAllAddedItem();
    }
}
