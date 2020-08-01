package com.flagcamp.donationcollector.ui.both;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentLocationSelectorBinding;

import java.util.Objects;

public class LocationSelectorFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentLocationSelectorBinding binding;
    private static final String TAG = "LocationSelectorFrag";
    EditText streetInput;
    EditText aptNumberInput;
    EditText cityInput;
    Spinner stateSpinner;
    EditText zipcodeInput;
    TextView testText;
    String state;
    Button doneButton;
    Button cancelButton;
    String fullAddress;
    String imagePath;
    String[] schedulesArray;

    public LocationSelectorFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentLocationSelectorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Current in onViewCreated method");
        String fromLocation = LocationSelectorFragmentArgs.fromBundle(getArguments()).getFromLocation();
        streetInput = binding.streetInput;
        aptNumberInput = binding.aptNumberInput;
        cityInput = binding.cityInput;
        stateSpinner = (Spinner) binding.stateInput;
        zipcodeInput = binding.zipcodeInput;
        testText = binding.testText;
        doneButton = binding.doneButton;
        cancelButton = binding.cancelButton;

        imagePath = LocationSelectorFragmentArgs.fromBundle(getArguments()).getImagePath();
        schedulesArray = LocationSelectorFragmentArgs.fromBundle(getArguments()).getSchedules();

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
                String aptNumber = aptNumberInput.getText().toString().equals("(Optional)") ? "" : aptNumberInput.getText().toString();
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
                Log.d("Done Button", aptNumberInput.getText().toString());

                fullAddress = street + ", " + (aptNumber.equals("") ? "" : (aptNumber + ", ")) + city + ", "
                        + state + " " + zipcode;
                testText.setText(fullAddress);

                LocationSelectorFragmentDirections.ActionTitleLocationToPostsPreview actionTitleLocationToPostsPreview =
                        LocationSelectorFragmentDirections.actionTitleLocationToPostsPreview();
                actionTitleLocationToPostsPreview.setLocation(fullAddress);
                actionTitleLocationToPostsPreview.setImagePath(imagePath);
                actionTitleLocationToPostsPreview.setSchedules(schedulesArray);
                NavHostFragment.findNavController(LocationSelectorFragment.this).navigate(actionTitleLocationToPostsPreview);
            }
        });



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationSelectorFragmentDirections.ActionTitleLocationToPostsPreview actionTitleLocationSelectorToPostsPreview =
                        LocationSelectorFragmentDirections.actionTitleLocationToPostsPreview();
                actionTitleLocationSelectorToPostsPreview.setLocation(null);
                actionTitleLocationSelectorToPostsPreview.setImagePath(imagePath);
                actionTitleLocationSelectorToPostsPreview.setSchedules(schedulesArray);
                NavHostFragment.findNavController(LocationSelectorFragment.this).navigate(actionTitleLocationSelectorToPostsPreview);
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
