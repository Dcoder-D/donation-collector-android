package com.flagcamp.donationcollector.ui.user.posts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
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
import com.flagcamp.donationcollector.model.PostItem;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.repository.SignInRepository;
import com.flagcamp.donationcollector.signin.AppUser;
import com.flagcamp.donationcollector.ui.ngo.posts.PostCenterAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import retrofit2.Response;

public class PostsPreviewFragment extends Fragment
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FragmentPostsPreviewBinding binding;
    private static final String TAG = "PostsPreviewFragment";
    private PostsPreviewViewModel viewModel;
    private String category;
    private String imagePath;
    private ImageView addedImage;
    private TextView displayLocation;
    private String location;
    private TextView displaySchedule;
    private List<String> schedules;
    private String[] schedulesArray;
    private List<PostItem> postItemList;
    private List<String> imagesPath;
    private AppUser[] appUsers;

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
        postItemList = new ArrayList<>();
        appUsers = new AppUser[1];
        imagesPath = new ArrayList<>();

        imagePath = PostsPreviewFragmentArgs.fromBundle(getArguments()).getImagePath();
        if(imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            addedImage.setImageBitmap(bitmap);
        }


        displayLocation = binding.locationDisplayText;
        displaySchedule = binding.scheduleDisplayText;

        location = PostsPreviewFragmentArgs.fromBundle(getArguments()).getLocation();
        if(location != null) {
            displayLocation.setText(location);
        }

        schedulesArray = PostsPreviewFragmentArgs.fromBundle(getArguments()).getSchedules();
        if(schedulesArray != null) {
            schedules = Arrays.asList(schedulesArray);
            displaySchedule.setText(schedules.toString());
        }

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

        binding.addAnotherPhoto.setOnClickListener(this);


        PostsPreviewAdapter postsPreviewAdapter = new PostsPreviewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.addedItemRecyclerView.setLayoutManager(linearLayoutManager);
        binding.addedItemRecyclerView.setAdapter(postsPreviewAdapter);

        PostRepository repository = new PostRepository(getContext());
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository)).get(PostsPreviewViewModel.class);
        // display the added items in recycler view

        SignInRepository signInRepository = new SignInRepository();
        signInRepository.getAppUser().observe(getViewLifecycleOwner(), response -> {
            if(response != null) {
                appUsers[0] = response.get(0);
            } else {

            }
        });

        viewModel.getAddedItems().observe(getViewLifecycleOwner(), items -> {
            if (items != null) {
                Log.d(TAG, "In Posts Preview Recycler View");
                postsPreviewAdapter.setItems(items);
                setPostItemsList(items);
            } else {
                Log.d(TAG, "No PostsPreview response");
            }
        });

        binding.postAllItemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Response myResponse;
                if(postItemList != null) {
                    myResponse = viewModel.postAllAddedItem(postItemList, imagesPath);
                    if(myResponse != null) {
                        Toast.makeText(getContext(), myResponse.message(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("PostsPreviewFragment", "myResponse null");
                    }

                } else {
                    Toast.makeText(getContext(), "postItemList still empty", Toast.LENGTH_SHORT).show();
                }

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

    public void setPostItemsList(List<Item> items) {
        postItemList.clear();
        imagesPath.clear();
        for(Item item: items) {
            imagesPath.add(item.urlToImage);
            PostItem postItem = new PostItem(item, appUsers[0]);
            postItemList.add(postItem);
        }
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
                item.availableTime = schedules;
                item.location = location;
                addItemAsAdded(item);
                break;
            case R.id.add_location_button:
                // TODO: add location here
                PostsPreviewFragmentDirections.ActionTitlePostsPreviewToLocation actionTitlePostsPreviewToLocation =
                        PostsPreviewFragmentDirections.actionTitlePostsPreviewToLocation("PostsPreviewFragment");
                actionTitlePostsPreviewToLocation.setImagePath(imagePath);
                actionTitlePostsPreviewToLocation.setSchedules(schedulesArray);
                NavHostFragment.findNavController(PostsPreviewFragment.this).navigate(actionTitlePostsPreviewToLocation);
                break;
            case R.id.add_schedule_button:
                // TODO: add schedule here
                PostsPreviewFragmentDirections.ActionTitlePostsPreviewPostsSchedule actionTitlePostsPreviewPostsSchedule =
                        PostsPreviewFragmentDirections.actionTitlePostsPreviewPostsSchedule();
                actionTitlePostsPreviewPostsSchedule.setImagePath(imagePath);
                actionTitlePostsPreviewPostsSchedule.setLocation(location);
                NavHostFragment.findNavController(PostsPreviewFragment.this).navigate(actionTitlePostsPreviewPostsSchedule);
                break;
            case R.id.post_all_item_button:
                // TODO: post all items to backend
                Response myResponse = postAllAddedItem();
                Toast.makeText(getContext(), myResponse.message(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_another_photo:
                NavHostFragment.findNavController(PostsPreviewFragment.this).navigate(R.id.action_title_posts_preview_to_albums);
                break;
        }
    }

    private void addItemAsAdded(Item item) {
        viewModel.addItemAsAdded(item);
    }

    private Response postAllAddedItem() {
        List<Item> items = new ArrayList<>();
        PostRepository repository = new PostRepository(getContext());
        PostsPreviewViewModel localViewModel = new ViewModelProvider(PostsPreviewFragment.this, new PostViewModelFactory(repository)).get(PostsPreviewViewModel.class);
        localViewModel.getAddedItems().observe(getViewLifecycleOwner(), response -> {
            if(response != null) {
                Log.d("getAdditems", response.toString());
                items.addAll(response);
            } else {
                Log.d("getAdditems", "null response");
            }

        });


        List<String> imagesPath = new ArrayList<>();
        List<PostItem> postItems = new ArrayList<>();
        SignInRepository signInRepository = new SignInRepository();
        AppUser[] appUsers = new AppUser[1];
        signInRepository.getAppUser().observe(getViewLifecycleOwner(), response -> {
            appUsers[0] = response.get(0);
        });
        for(Item item: items) {
            imagesPath.add(item.urlToImage);
            PostItem postItem = new PostItem(item, appUsers[0]);
            postItems.add(postItem);
        }
        return viewModel.postAllAddedItem(postItems, imagesPath);
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