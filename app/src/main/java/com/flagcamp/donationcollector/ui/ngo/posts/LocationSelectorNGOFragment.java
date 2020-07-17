package com.flagcamp.donationcollector.ui.ngo.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;

import com.flagcamp.donationcollector.databinding.FragmentLocationSelectorNgoBinding;

public class LocationSelectorNGOFragment extends Fragment {

    private FragmentLocationSelectorNgoBinding binding;
    Button confirmButton;

    public LocationSelectorNGOFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentLocationSelectorNgoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        confirmButton = view.findViewById(R.id.location_confirm_ngo_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationSelectorNGOFragmentDirections.ActionTitleLocationSelectorToPostcenter actionTitleLocationSelectorToPostcenter =
                        LocationSelectorNGOFragmentDirections.actionTitleLocationSelectorToPostcenter();
                actionTitleLocationSelectorToPostcenter.setDummy("test back");
                NavHostFragment.findNavController(LocationSelectorNGOFragment.this).navigate(actionTitleLocationSelectorToPostcenter);
            }
        });
    }
}
