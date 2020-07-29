package com.flagcamp.donationcollector.ui.user.posts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.SignInRepository;
import com.flagcamp.donationcollector.signin.AppUser;

import java.util.List;

public class PostUserViewModel extends ViewModel {

    private final PostRepository repository;
    private  final SignInRepository signInRepository;

    public PostUserViewModel(PostRepository repository, SignInRepository signInRepository) {

        this.repository = repository;
        this.signInRepository = signInRepository;
    }

    public LiveData<List<AppUser>> getAppUser() {
        return signInRepository.getAppUser();
    }

    public LiveData<List<Item>> getUserPosts(String posterId) {
        return repository.getUserPosts(posterId);
    }
}
