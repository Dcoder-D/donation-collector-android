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
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.ui.both.calendar.ScheduledPickupAdapter;
import com.flagcamp.donationcollector.ui.both.calendar.ScheduledPickupViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarNGOFragment extends Fragment {
    private FragmentCalendarNgoBinding binding;
    private ScheduledPickupViewModel viewModel;

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
        binding.calendarNGO.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "-0" + (month + 1) + "-" + dayOfMonth;
                Log.d("DATE", date);

                CalendarNGOFragmentDirections.ActionTitleCalendarngoToScheduledpickup actionTitleCalendarngoToScheduledpickup =
                        CalendarNGOFragmentDirections.actionTitleCalendarngoToScheduledpickup();
                actionTitleCalendarngoToScheduledpickup.setDate(date);
                //TODO: fix hardcoded NGOId
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

        //TODO: add to factory
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository))
                .get(ScheduledPickupViewModel.class);

        //Getting today's date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todaysDate = df.format(c);

        //TODO: Get ID from
        viewModel.getNGODateEquals(todaysDate, "1").observe(getViewLifecycleOwner(), postResponse -> {
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
