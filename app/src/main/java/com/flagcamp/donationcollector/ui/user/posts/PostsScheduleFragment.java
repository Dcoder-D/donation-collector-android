package com.flagcamp.donationcollector.ui.user.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentPostsShceduleBinding;

import java.util.ArrayList;
import java.util.List;

public class PostsScheduleFragment extends Fragment implements View.OnClickListener{
    FragmentPostsShceduleBinding binding;
    private EditText scheduleInput;
    private TextView displaySchedule;
    private List<String> schedules;
    private String imagePath;
    private String location;
    private StringBuilder sb;
    private DatePicker picker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentPostsShceduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        picker = binding.datePicker1;
        displaySchedule = binding.displayShcedule;

        imagePath = PostsScheduleFragmentArgs.fromBundle(getArguments()).getImagePath();
        location = PostsScheduleFragmentArgs.fromBundle(getArguments()).getLocation();

        binding.scheduleAddScheduleButton.setOnClickListener(this);
        binding.addAllScheduleButton.setOnClickListener(this);

        sb = new StringBuilder();

        schedules = new ArrayList<>();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.schedule_add_schedule_button:
                String month = String.valueOf(picker.getMonth());
                String day = String.valueOf(picker.getDayOfMonth());
                String year = String.valueOf(picker.getYear());
                String date = year + "-" + (month.length() < 2 ? "0" + month : month) + "-" + (day.length() < 2 ? "0" + day : day);
                if(!isValidDate(date)) {
                    Toast.makeText(getContext(), "The date format is not correct", Toast.LENGTH_SHORT).show();
                    return;
                }
                schedules.add(date);

                sb.append(date);
                sb.append("\n");

                displaySchedule.setText(sb.toString());
                break;
            case R.id.add_all_schedule_button:
                String[] schedulesArray = new String[schedules.size()];
                for(int i = 0; i < schedules.size(); i++) {
                    schedulesArray[i] = schedules.get(i);
                }
                PostsScheduleFragmentDirections.ActionTitlePostsScheduleToPostsPreview actionTitlePostsScheduleToPostsPreview =
                        PostsScheduleFragmentDirections.actionTitlePostsScheduleToPostsPreview();
                actionTitlePostsScheduleToPostsPreview.setSchedules(schedulesArray);
                actionTitlePostsScheduleToPostsPreview.setImagePath(imagePath);
                actionTitlePostsScheduleToPostsPreview.setLocation(location);
                NavHostFragment.findNavController(PostsScheduleFragment.this).navigate(actionTitlePostsScheduleToPostsPreview);
                break;
        }
    }

    private boolean isValidDate(String input) {
        char[] array = input.toCharArray();
        // Format must be YYYY-mm-dd
        if(array.length != 10 || array[4] != '-' || array[7] != '-') {
            return false;
        } else {
            // TODO: check the day for different month, and year
            int month = toNumber(array, 5, 6);
            int day = toNumber(array, 8,9);
            int year = toNumber(array, 0, 3);
            if(month < 1 || month > 12) return false;
            if(day < 1 || day > 31) return false;
            if(year == -1) return false;
        }
        return true;
    }

    private int toNumber(char[] input, int start, int end) {
        int result = 0;
        for(int i = start; i <= end; i++) {
            if(input[i] < '0' || input[i] > '9') {
                return -1;
            }
            result = result * 10 + input[i] - '0';
        }
        return result;
    }
}
