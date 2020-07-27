package com.flagcamp.donationcollector.ui.both.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flagcamp.donationcollector.databinding.FragmentScheduledPickupBinding;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.ui.ngo.posts.PostCenterViewModel;

import java.util.List;

public class ScheduledPickupFragment extends Fragment {
    private FragmentScheduledPickupBinding binding;
    private ScheduledPickupViewModel viewModel;

    public ScheduledPickupFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentScheduledPickupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(getArguments() == null) {
            return;
        }

        String date = ScheduledPickupFragmentArgs.fromBundle(getArguments()).getDate();
        String NGOId = ScheduledPickupFragmentArgs.fromBundle(getArguments()).getNGOId();

        ScheduledPickupAdapter scheduledPickupAdapter = new ScheduledPickupAdapter(this);
        binding.scheduledPickupRecyclerView.setAdapter(scheduledPickupAdapter);
        binding.scheduledPickupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        PostRepository repository = new PostRepository(getContext());

        //TODO: add to factory
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository))
                .get(ScheduledPickupViewModel.class);

        //TODO: Get ID from
        viewModel.getNGODateEquals(date, NGOId).observe(getViewLifecycleOwner(), postResponse -> {
            if(postResponse != null) {
                Log.d("ScheduledPickupFragment", "Success");
                Log.d("ScheduledPickupFragment", postResponse.toString());
                scheduledPickupAdapter.setItems(postResponse);
            } else {
                Log.d("ScheduledPickupFragment", "Null postResponse");
            }
        });

    }
}
