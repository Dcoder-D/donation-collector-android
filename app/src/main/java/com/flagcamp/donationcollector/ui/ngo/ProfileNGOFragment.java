package com.flagcamp.donationcollector.ui.ngo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flagcamp.donationcollector.databinding.FragmentProfileNgoBinding;

public class ProfileNGOFragment extends Fragment {

    private FragmentProfileNgoBinding binding;

    public ProfileNGOFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentProfileNgoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
