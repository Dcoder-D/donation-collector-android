package com.flagcamp.donationcollector.ui.ngo.posts;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;

import com.flagcamp.donationcollector.databinding.FragmentLocationSelectorNgoBinding;
import com.flagcamp.donationcollector.model.Location;
import com.flagcamp.donationcollector.repository.LocationRepository;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.ui.both.LocationSelectorFragment;
import com.flagcamp.donationcollector.ui.both.LocationSelectorFragmentArgs;
import com.flagcamp.donationcollector.ui.both.LocationSelectorFragmentDirections;

public class LocationSelectorNGOFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentLocationSelectorNgoBinding binding;
    Button confirmButton;
    EditText streetInput;
    EditText cityInput;
    Spinner stateSpinner;
    EditText zipcodeInput;
    TextView testText;
    String state;
    EditText distanceInput;
    String distance;
    Button doneButton;
    Button cancelButton;
    String fullAddress;

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
        String fromLocation = LocationSelectorFragmentArgs.fromBundle(getArguments()).getFromLocation();
        streetInput = binding.streetInput;
        cityInput = binding.cityInput;
        stateSpinner = (Spinner) binding.stateInput;
        zipcodeInput = binding.zipcodeInput;
        distanceInput = binding.distanceInput;
        doneButton = binding.doneButton;
        cancelButton = binding.cancelButton;

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.states_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.states_array)) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
        stateSpinner.setOnItemSelectedListener(this);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String street = streetInput.getText().toString();
                String city = cityInput.getText().toString();
                String zipcode = zipcodeInput.getText().toString();
                if (street.length() == 0 || city.length() == 0 || zipcode.length() == 0) {
                    Toast.makeText(getContext(), "Please enter your full address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (state.equals("Select a state")) {
                    Toast.makeText(getContext(), "Please select a state", Toast.LENGTH_SHORT).show();
                    return;
                }
                fullAddress = street + ", " + city + ", "
                        + state + " " + zipcode;

                distance = distanceInput.getText().toString();
                if (distance.length() == 0) {
                    Toast.makeText(getContext(), "Please enter a distance", Toast.LENGTH_SHORT).show();
                    return;
                }

                PostCenterFragment.setLocationDistance(fullAddress, distance);

                LocationSelectorNGOFragmentDirections.ActionTitleLocationSelectorToPostcenter actionTitleLocationSelectorToPostcenter =
                        LocationSelectorNGOFragmentDirections.actionTitleLocationSelectorToPostcenter();
                actionTitleLocationSelectorToPostcenter.setLocation(fullAddress);
                actionTitleLocationSelectorToPostcenter.setDistance(distance);

//                Location location = new Location();
//                location.location = fullAddress;
//                location.distance = distance;

//                PostRepository repository = new PostRepository(getContext());
//                repository.deleteAllLocation();
//                repository.insertLocation(location);

                NavHostFragment.findNavController(LocationSelectorNGOFragment.this).navigate(actionTitleLocationSelectorToPostcenter);
            }
        });



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationSelectorNGOFragmentDirections.ActionTitleLocationSelectorToPostcenter actionTitleLocationSelectorToPostcenter
                        = LocationSelectorNGOFragmentDirections.actionTitleLocationSelectorToPostcenter();
                actionTitleLocationSelectorToPostcenter.setLocation(null);
                NavHostFragment.findNavController(LocationSelectorNGOFragment.this).navigate(actionTitleLocationSelectorToPostcenter);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        state = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        state = parent.getItemAtPosition(0).toString();
    }
}
