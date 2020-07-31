package com.flagcamp.donationcollector.ui.user.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flagcamp.donationcollector.databinding.FragmentUserScheduledPickupBinding;
import com.flagcamp.donationcollector.databinding.FragmentUserScheduledPickupBinding;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.repository.SignInRepository;
import com.flagcamp.donationcollector.ui.both.calendar.ScheduledPickupAdapter;
import com.flagcamp.donationcollector.ui.ngo.calendar.NGOScheduledPickupFragmentArgs;
import com.flagcamp.donationcollector.ui.ngo.calendar.NGOScheduledPickupViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserScheduledPickupFragment extends Fragment {
    private FragmentUserScheduledPickupBinding binding;
    private UserScheduledPickupViewModel viewModel;
    private List<Item> scheduledItems;
    TextView message;

    public UserScheduledPickupFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentUserScheduledPickupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(getArguments() == null) {
            return;
        }

        String date = UserScheduledPickupFragmentArgs.fromBundle(getArguments()).getDate();
        String NGOId = NGOScheduledPickupFragmentArgs.fromBundle(getArguments()).getNGOId();

        scheduledItems = new ArrayList<>();

        ScheduledPickupAdapter scheduledPickupAdapter = new ScheduledPickupAdapter(this);
        binding.scheduledPickupRecyclerView.setAdapter(scheduledPickupAdapter);
        binding.scheduledPickupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        message = binding.text;

        message.setText("Scheduled Pickup for\n" + date);

        PostRepository repository = new PostRepository(getContext());

        //TODO: add to factory
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository))
                .get(UserScheduledPickupViewModel.class);


        SignInRepository signInRepository = new SignInRepository();
        signInRepository.getAppUser().observe(getViewLifecycleOwner(), response -> {
            if(response != null) {
                Log.d("UserScheduledPickup", "The date is: " + date);
                viewModel.getUserPosts(response.get(0).getUid()).observe(getViewLifecycleOwner(), posts -> {
                    for(Item item: posts) {
                        Log.d("UserScheduledPickup", "The date of item: " + item.pickupTime);
                        if(item.status.toLowerCase().equals("scheduled") && item.pickupTime.equals(date)) {
                            scheduledItems.add(item);
                        }
                    }
                    scheduledPickupAdapter.setItems(scheduledItems);
                });
            }
        });

//        //TODO: Get ID from
//        viewModel.getNGODateEquals(date, NGOId).observe(getViewLifecycleOwner(), postResponse -> {
//            if(postResponse != null) {
//                Log.d("ScheduledPickupFragment", "Success");
//                Log.d("ScheduledPickupFragment", postResponse.toString());
//                scheduledPickupAdapter.setItems(postResponse);
//            } else {
//                Log.d("ScheduledPickupFragment", "Null postResponse");
//            }
//        });

    }
}
