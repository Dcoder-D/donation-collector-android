package com.flagcamp.donationcollector.ui.user.posts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentPostsPreviewBinding;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.ui.ngo.posts.PostCenterAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostsPreviewFragment extends Fragment
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FragmentPostsPreviewBinding binding;
    private static final String TAG = "PostsPreviewFragment";
    private PostsPreviewViewModel viewModel;
    private String category;
    private String imagePath;
    private ImageView addedImage;

    public PostsPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostsPreviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.categorySpinner.setOnItemSelectedListener(this);
        addedImage = binding.addedImage;
        imagePath = PostsPreviewFragmentArgs.fromBundle(getArguments()).getImagePath();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        addedImage.setImageBitmap(bitmap);

        // Creating adapter for spinner
        List<String> categories = getAllCategory();
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
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
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        binding.categorySpinner.setAdapter(dataAdapter);
        binding.addLocationButton.setOnClickListener(this);
        binding.addScheduleButton.setOnClickListener(this);
        binding.addNewItemButton.setOnClickListener(this);
        binding.postAllItemButton.setOnClickListener(this);


        PostsPreviewAdapter postsPreviewAdapter = new PostsPreviewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.addedItemRecyclerView.setLayoutManager(linearLayoutManager);
        binding.addedItemRecyclerView.setAdapter(postsPreviewAdapter);

        PostRepository repository = new PostRepository(getContext());
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository)).get(PostsPreviewViewModel.class);
        // display the added items in recycler view
        viewModel.getAddedItems().observe(getViewLifecycleOwner(), items -> {
            if (items != null) {
                Log.d(TAG, "In Posts Preview Recycler View");
                postsPreviewAdapter.setItems(items);
            } else {
                Log.d(TAG, "No PostsPreview response");
            }
        });
    }

    private List<String> getAllCategory() {
        List<String> result = new ArrayList<>();
        result.add("Select a category");
        result.add("Apparel");
        result.add("Electronics");
        result.add("Entertainment");
        result.add("Hobbies");
        result.add("Furniture");
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_new_item_button:
                if (category.equals("Select a category")) {
                    Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_SHORT).show();
                    return;
                }
                String description = binding.textItemDescription.getText().toString();
                if (description.length() == 0) {
                    Toast.makeText(getContext(), "Please write a description", Toast.LENGTH_SHORT).show();
                    return;
                }
                Item item = new Item();
                item.id = UUID.randomUUID().toString();
                item.urlToImage = imagePath;
                item.description = description;
                item.category = category;
                item.status = "added";
                addItemAsAdded(item);
                break;
            case R.id.add_location_button:
                // TODO: add location here
                break;
            case R.id.add_schedule_button:
                // TODO: add schedule here
                break;
            case R.id.post_all_item_button:
                // TODO: post all items to backend
                postAllAddedItem();
                break;
        }
    }

    private void addItemAsAdded(Item item) {
        viewModel.addItemAsAdded(item);
    }

    private void postAllAddedItem() {
        viewModel.postAllAddedItem();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setCategory(parent.getItemAtPosition(position).toString());
        Log.d(TAG, "Selected item category is " + category);
    }

    private void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}