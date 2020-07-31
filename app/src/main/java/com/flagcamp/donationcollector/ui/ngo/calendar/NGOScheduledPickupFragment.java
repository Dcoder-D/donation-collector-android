package com.flagcamp.donationcollector.ui.ngo.calendar;

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
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.repository.SignInRepository;
import com.flagcamp.donationcollector.signin.AppUser;
import com.flagcamp.donationcollector.ui.both.calendar.ScheduledPickupAdapter;

import java.util.ArrayList;
import java.util.List;

public class NGOScheduledPickupFragment extends Fragment {
    private FragmentUserScheduledPickupBinding binding;
    private NGOScheduledPickupViewModel viewModel;
    private List<Item> scheduledItems;
    private TextView message;

    public NGOScheduledPickupFragment() {}

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

        String date = NGOScheduledPickupFragmentArgs.fromBundle(getArguments()).getDate();
        String NGOId = NGOScheduledPickupFragmentArgs.fromBundle(getArguments()).getNGOId();

        ScheduledPickupAdapter scheduledPickupAdapter = new ScheduledPickupAdapter(this);
        binding.scheduledPickupRecyclerView.setAdapter(scheduledPickupAdapter);
        binding.scheduledPickupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        message = binding.text;
        message.setText("Scheduled Pickup for\n" + date);

        PostRepository repository = new PostRepository(getContext());

        //TODO: add to factory
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository))
                .get(NGOScheduledPickupViewModel.class);


        SignInRepository signInRepository = new SignInRepository();

        signInRepository.getAppUser().observe(getViewLifecycleOwner(), response -> {
            if(response != null) {
                AppUser appUser = response.get(0);
                viewModel.getNGOPosts(appUser.getUid()).observe(getViewLifecycleOwner(), itemResponse -> {
                    if(itemResponse != null) {
                        Log.d("CalendarNGOFragment", "response returns: " + itemResponse.toString());

                        scheduledItems = new ArrayList<>();
                        for(Item item: itemResponse) {
                            if(item.pickupTime.equals(date)) {
                                scheduledItems.add(item);
                            }
                        }

                        scheduledPickupAdapter.setItems(scheduledItems);

                    } else {
                        Log.d("CalendarNGOFragment", "response returns null");
                        scheduledPickupAdapter.setItems(null);
                    }
                });
            } else {

            }
        });


        //TODO: Get ID from
//        viewModel.getNGODateEquals(date, NGOId).observe(getViewLifecycleOwner(), postResponse -> {
////            if(postResponse != null) {
////                Log.d("ScheduledPickupFragment", "Success");
////                Log.d("ScheduledPickupFragment", postResponse.toString());
////                scheduledPickupAdapter.setItems(postResponse);
////            } else {
////                Log.d("ScheduledPickupFragment", "Null postResponse");
////            }
//            scheduledPickupAdapter.setItems(scheduledItems);
//        });
//        if(scheduledItems == null) {
//            Log.d("NGOSchedulePickup", "scheduledItems is null");
//        } else {
//            scheduledPickupAdapter.setItems(scheduledItems);
//        }

    }

    public void setScheduledItems(List<Item> scheduledItems) {
        if(this.scheduledItems == null) {
            this.scheduledItems = scheduledItems;
        } else {
            this.scheduledItems.clear();
            this.scheduledItems.addAll(scheduledItems);
        }
    }
}
