package com.flagcamp.donationcollector.ui.ngo.posts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import java.util.List;

public class PostDetailsNGOFragment extends Fragment {

    private PostDetailsNGOViewModel viewModel;
    FragmentPostDetailsNgoBinding binding;
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetailsNGOFragmentDirections.ActionTitlePostDetailsToPostcenter actionTitlePostDetailsToPostcenter =
                        PostDetailsNGOFragmentDirections.actionTitlePostDetailsToPostcenter();
                actionTitlePostDetailsToPostcenter.setDummy("test");
                NavHostFragment.findNavController(PostDetailsNGOFragment.this).navigate(actionTitlePostDetailsToPostcenter);
            }
        });


        PostDetailsNGOAdapter postDetailsNGOAdapter = new PostDetailsNGOAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.detailsRecyclerView.setLayoutManager(linearLayoutManager);
        binding.detailsRecyclerView.setAdapter(postDetailsNGOAdapter);


        mitem = PostDetailsNGOFragmentArgs.fromBundle(getArguments()).getPost();

        if (mitem != null) {
            Picasso.get().load(mitem.urlToImage).into(binding.postDetailsUserImg);
            binding.category.setText(mitem.description);
            binding.size.setText("Size: " + mitem.size);
            binding.address.setText(mitem.location);

            postDetailsNGOAdapter.setDates(mitem.availableTime);
        } else {
            throw new IllegalArgumentException("has no info to show details");
        }


        PostRepository repository = new PostRepository(getContext());
        viewModel = new ViewModelProvider(this, new PostViewModelFactory(repository)).get(PostDetailsNGOViewModel.class);

        confirmButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable input = binding.detailsPickupTime.getText();
                String dateTime = input.toString();
                if (dateTime.length() >= 10 && isDateValid(dateTime.substring(0, 10))) {
                    appUser = (AppUser) getActivity().getIntent().getSerializableExtra("AppUser");
                    String ngoId = appUser.getUid();
                    naviToCenter();
//                    if (viewModel.confirmPickUp(mitem.id, ngoId)) {
//                        naviToCenter();
//                    } else {
//                        new AlertDialog.Builder(getContext())
//                                .setTitle("Sorry!")
//                                .setMessage("This item has been confirmed by others.")
//                                .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        naviToCenter();
//                                    }
//                                })
//                                .show();
//                    }

                } else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Error!")
                            .setMessage("Please input a valid pick-up time.")
                            .setPositiveButton("Got it", null)
                            .show();
                }
            }
        });
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
    private void naviToCenter(){
        PostDetailsNGOFragmentDirections.ActionTitlePostDetailsToPostcenter actionTitlePostDetailsToPostcenter =
                PostDetailsNGOFragmentDirections.actionTitlePostDetailsToPostcenter();
        actionTitlePostDetailsToPostcenter.setDummy("test");
        NavHostFragment.findNavController(PostDetailsNGOFragment.this).navigate(actionTitlePostDetailsToPostcenter);
    }
}
