package com.flagcamp.donationcollector.ui.ngo.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flagcamp.donationcollector.databinding.FragmentCalendarNgoBinding;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.repository.SignInRepository;
import com.flagcamp.donationcollector.signin.AppUser;
import com.flagcamp.donationcollector.ui.both.calendar.ScheduledPickupAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarNGOFragment extends Fragment {
    private FragmentCalendarNgoBinding binding;
    private NGOScheduledPickupViewModel viewModel;
    private List<Item> ngoPosts;

    public CalendarNGOFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentCalendarNgoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ngoPosts = new ArrayList<>();
        binding.calendarNGO.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                String date = year + "-0" + (month + 1) + "-" + dayOfMonth;
                month += 1;
                String date = year + (month < 10 ? "-0" + String.valueOf(month) : String.valueOf(month)) + (dayOfMonth < 10 ? "-0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth));
                Log.d("DATE", date);

                CalendarNGOFragmentDirections.ActionTitleCalendarngoToScheduledpickup actionTitleCalendarngoToScheduledpickup =
                        CalendarNGOFragmentDirections.actionTitleCalendarngoToScheduledpickup();
                actionTitleCalendarngoToScheduledpickup.setDate(date);
                //TODO: fix hardcoded NGOId
                List<Item> scheduledItems = new ArrayList<>();
                for(Item item: ngoPosts) {
                    if(item.pickupTime.equals(date)) {
                        scheduledItems.add(item);
                    }
                }

                new NGOScheduledPickupFragment().setScheduledItems(scheduledItems);

                actionTitleCalendarngoToScheduledpickup.setNGOId("0");
                NavHostFragment.findNavController(CalendarNGOFragment.this)
                        .navigate(actionTitleCalendarngoToScheduledpickup);

                //RecyclerView

            }
        });

        ScheduledPickupAdapter scheduledPickupAdapter = new ScheduledPickupAdapter(this);
        binding.scheduledPickupRecyclerView.setAdapter(scheduledPickupAdapter);
        binding.scheduledPickupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        PostRepository repository = new PostRepository(getContext());

        SignInRepository signInRepository = new SignInRepository();

        //TODO: add to factory
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository))
                .get(NGOScheduledPickupViewModel.class);

        //Getting today's date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todaysDate = df.format(c);

//        viewModel.getNGODateEquals(todaysDate, "1").observe(getViewLifecycleOwner(), postResponse -> {
//            if(postResponse != null) {
//                Log.d("ScheduledPickupFragment", "Success");
//                Log.d("ScheduledPickupFragment", postResponse.toString());
//                scheduledPickupAdapter.setItems(postResponse);
//            } else {
//                Log.d("ScheduledPickupFragment", "Null postResponse");
//            }
//        });

        signInRepository.getAppUser().observe(getViewLifecycleOwner(), response -> {
            if(response != null) {
                AppUser appUser = response.get(0);
                viewModel.getNGOPosts(appUser.getUid()).observe(getViewLifecycleOwner(), itemResponse -> {
                    if(itemResponse != null) {
                        Log.d("CalendarNGOFragment", "response returns: " + itemResponse.toString());
                        scheduledPickupAdapter.setItems(itemResponse);
                        ngoPosts.clear();
                        ngoPosts.addAll(itemResponse);
                    } else {
                        Log.d("CalendarNGOFragment", "response returns null");
                        scheduledPickupAdapter.setItems(null);
                    }
                });
            } else {

            }
        });


    }
}
