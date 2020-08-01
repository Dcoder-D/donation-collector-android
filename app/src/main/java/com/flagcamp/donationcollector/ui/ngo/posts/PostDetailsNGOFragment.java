package com.flagcamp.donationcollector.ui.ngo.posts;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flagcamp.donationcollector.databinding.FragmentPostDetailsNgoBinding;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.signin.AppUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostDetailsNGOFragment extends Fragment {

    private PostDetailsNGOViewModel viewModel;
    FragmentPostDetailsNgoBinding binding;
    EditText editText;
    Button backButton;
    Button confirmButtion;
    private AppUser appUser;
    private static Item mitem;

    public PostDetailsNGOFragment() {

    }
    public static void setItem(Item item) {
        mitem = item;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentPostDetailsNgoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = binding.postDetailsNgoBackButton;
        confirmButtion = binding.postDetailsNgoScheduleButton;
        editText = binding.detailsPickupTime;
        mitem = PostDetailsNGOFragmentArgs.fromBundle(getArguments()).getPost();
        appUser = (AppUser) getActivity().getIntent().getSerializableExtra("AppUser");

        PostRepository repository = new PostRepository(getContext());
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository)).get(PostDetailsNGOViewModel.class);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetailsNGOFragmentDirections.ActionTitlePostDetailsToPostcenter actionTitlePostDetailsToPostcenter =
                        PostDetailsNGOFragmentDirections.actionTitlePostDetailsToPostcenter();
                actionTitlePostDetailsToPostcenter.setDummy("test");
                NavHostFragment.findNavController(PostDetailsNGOFragment.this).navigate(actionTitlePostDetailsToPostcenter);
            }
        });


        setOnEdit();
        setRecycleView();

        confirmButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = mitem.status;
                if (status.equals("PENDING")) {
                    schedulePickUp();
                }else if (status.equals("SCHEDULED")) {
                    confirmPickUp();
                }
            }
        });
    }

    private void setOnEdit() {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);

                // show date picker dialog
                new DatePickerDialog(getContext(),android.R.style.Theme_DeviceDefault_Light_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String sMonthOfYear = dateTimeToString(monthOfYear + 1);
                                String sDayOfMonth = dateTimeToString(dayOfMonth);
                                String date = year + "-" + sMonthOfYear + "-" + sDayOfMonth;
                                editText.setText(date);

//                                //show time picker dialog after date is set.
//                                new TimePickerDialog(getContext(),android.R.style.Theme_DeviceDefault_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
//                                    @Override
//                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
//                                    {
//                                        String sHourOfDay = dateTimeToString(hourOfDay);
//                                        String sMinute = dateTimeToString(minute);
//                                        editText.setText(date + "  " + sHourOfDay + " : " + sMinute);
//                                    }
//                                }, hour, minute, true).show();
                            }
                        }, year, month, day).show();
            }
        });
    }

    private void setRecycleView() {
        PostDetailsNGOAdapter postDetailsNGOAdapter = new PostDetailsNGOAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.detailsRecyclerView.setLayoutManager(linearLayoutManager);
        binding.detailsRecyclerView.setAdapter(postDetailsNGOAdapter);

        if (mitem != null) {
            Picasso.get().load(mitem.urlToImage).into(binding.postDetailsUserImg);
            binding.category.setText(mitem.description);
            binding.size.setText("Size: " + mitem.size);
            binding.address.setText(mitem.location);

            String status = mitem.status;
            List<String> dates = new ArrayList<>();
            if (status.equals("PENDING")) {
                dates = mitem.availableTime;
            }else if (status.equals("SCHEDULED")) {
                dates.add(mitem.pickupTime);
                editText.setVisibility(View.INVISIBLE);
            }
            postDetailsNGOAdapter.setDates(status, dates);
        } else {
            throw new IllegalArgumentException("has no info to show details");
        }
    }

    private void confirmPickUp() {
        String itemId = mitem.id;
        String ngoId = appUser.getUid();
        boolean response = viewModel.confirmPickUp(itemId, ngoId);
        if (response) {
            Toast.makeText(getContext(), "Pick-up completes.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Network error, please try again.", Toast.LENGTH_SHORT).show();
        }
        naviToCenter();
    }

    private void schedulePickUp() {
        Editable input = binding.detailsPickupTime.getText();
        String dateTime = input.toString();
        if (dateTime.length() >= 10 && isDateValid(dateTime.substring(0, 10))) {
            String itemId = mitem.id;
            String ngoId = appUser.getUid();
            String ngoName = appUser.getOrganizationName();
            String pickUpDate = dateTime;
            boolean response = viewModel.schedulePickUp(itemId, ngoId, ngoName, pickUpDate);
            if (response) {
                Toast.makeText(getContext(), "Schedule successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Schedule not successful", Toast.LENGTH_SHORT).show();
            }
            naviToCenter();
        } else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Error!")
                    .setMessage("Please input a valid pick-up time.")
                    .setPositiveButton("Got it", null)
                    .show();
        }
    }

    private boolean isDateValid(String date) {
        SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-DD");
        try {
            str.parse(date);
            return isDateAvailable(date);
        } catch (java.text.ParseException e) {
            return false;
        }
    }

    private boolean isDateAvailable(String date) {
        List<String> dates = mitem.availableTime;
        for (String d : dates) {
            if (date.equals(d)) {
                return true;
            }
        }
        return false;
    }
    private String dateTimeToString(int dateTime) {
        String res = dateTime + "";
        if (dateTime < 10) {
            res = "0" + res;
        }
        return res;
    }
    private void naviToCenter(){
        PostDetailsNGOFragmentDirections.ActionTitlePostDetailsToPostcenter actionTitlePostDetailsToPostcenter =
                PostDetailsNGOFragmentDirections.actionTitlePostDetailsToPostcenter();
        actionTitlePostDetailsToPostcenter.setDummy("test");
        NavHostFragment.findNavController(PostDetailsNGOFragment.this).navigate(actionTitlePostDetailsToPostcenter);
    }
}
