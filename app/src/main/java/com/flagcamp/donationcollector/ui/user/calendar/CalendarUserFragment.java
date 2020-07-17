package com.flagcamp.donationcollector.ui.user.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flagcamp.donationcollector.databinding.FragmentCalendarUserBinding;

public class CalendarUserFragment extends Fragment {

    private FragmentCalendarUserBinding binding;

    public CalendarUserFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentCalendarUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
