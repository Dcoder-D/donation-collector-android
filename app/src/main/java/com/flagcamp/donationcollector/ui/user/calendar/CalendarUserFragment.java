package com.flagcamp.donationcollector.ui.user.calendar;

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
import com.flagcamp.donationcollector.databinding.FragmentCalendarUserBinding;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.repository.SignInRepository;
import com.flagcamp.donationcollector.ui.both.calendar.ScheduledPickupAdapter;
import com.flagcamp.donationcollector.ui.ngo.calendar.NGOScheduledPickupViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarUserFragment extends Fragment {
    private FragmentCalendarUserBinding binding;
    private UserScheduledPickupViewModel viewModel;
    private List<Item> scheduledItems;

    public CalendarUserFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentCalendarUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scheduledItems = new ArrayList<>();
        binding.calendarUser.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                String date = year + (month < 10 ? "-0" + String.valueOf(month) : String.valueOf(month)) + (dayOfMonth < 10 ? "-0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth));
                Log.d("DATE", date);

                CalendarUserFragmentDirections.ActionTitleCalendaruserToScheduledpickup actionTitleCalendaruserToScheduledpickup =
                        CalendarUserFragmentDirections.actionTitleCalendaruserToScheduledpickup();
                actionTitleCalendaruserToScheduledpickup.setDate(date);
                //TODO: fix hardcoded UserId
                actionTitleCalendaruserToScheduledpickup.setUserId("0");
                NavHostFragment.findNavController(CalendarUserFragment.this)
                        .navigate(actionTitleCalendaruserToScheduledpickup);

                //RecyclerView

            }
        });

        ScheduledPickupAdapter scheduledPickupAdapter = new ScheduledPickupAdapter(this);
        binding.scheduledPickupRecyclerView.setAdapter(scheduledPickupAdapter);
        binding.scheduledPickupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        PostRepository repository = new PostRepository(getContext());

        //TODO: add to factory
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository))
                .get(UserScheduledPickupViewModel.class);

        //Getting today's date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todaysDate = df.format(c);

        SignInRepository signInRepository = new SignInRepository();
        signInRepository.getAppUser().observe(getViewLifecycleOwner(), response -> {
            if(response != null) {
                viewModel.getUserPosts(response.get(0).getUid()).observe(getViewLifecycleOwner(), posts -> {
                    for(Item item: posts) {
                        if(item.status.toLowerCase().equals("scheduled")) {
                            scheduledItems.add(item);
                        }
                    }
                    scheduledPickupAdapter.setItems(scheduledItems);
                });
            }
        });

    }
}
