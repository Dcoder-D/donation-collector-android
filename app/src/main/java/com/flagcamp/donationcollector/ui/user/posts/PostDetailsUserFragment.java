package com.flagcamp.donationcollector.ui.user.posts;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.databinding.FragmentPostDetailsUserBinding;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.repository.PostRepository;
import com.flagcamp.donationcollector.repository.PostViewModelFactory;
import com.flagcamp.donationcollector.repository.SignInRepository;
import com.flagcamp.donationcollector.signin.AppUser;
import com.squareup.picasso.Picasso;

public class PostDetailsUserFragment extends Fragment {

    private PostDetailsUserViewModel ViewModel;
    private FragmentPostDetailsUserBinding binding;
    private static Item mitem;
    public PostDetailsUserFragment() {
    }

    public static void setItem(Item item) {
        mitem = item;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPostDetailsUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mitem != null) {


            Picasso.get().load(mitem.urlToImage).into(binding.postDetailsUserImg);

//            Picasso.with(getContext()).load(mitem.urlToImage).into(binding.postDetailsUserImg);

            binding.category.setText(mitem.category);
            binding.size.setText("Size: " + mitem.size);
            String status = mitem.status;
            if (status.toLowerCase().equals("pending")) {
                binding.status.setText("Pending");
                binding.status.setBackgroundResource(R.color.light_blue);
                binding.status.setGravity(Gravity.CENTER);
            }else{
                String time = mitem.pickupTime;
                String ngoName = mitem.ngoUser.ngoName;
                String s = "Scheduled\nDate: " + time + "\nOrganization: " + ngoName;
                binding.status.setText(s);
                binding.status.setBackgroundResource(R.color.light_green);
            }
        }else {
            throw new IllegalArgumentException("has no info to show details");
        }

        binding.postDetailsUserBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_details_to_user);
            }
        });


        PostRepository repository = new PostRepository(getContext());
        SignInRepository signInRepository = new SignInRepository();
//        AppUser appUser = signInRepository.getAppUser().getValue().get(0);
        AppUser appUser[] = new AppUser[1];
        signInRepository.getAppUser().observe(getViewLifecycleOwner(), response -> {
            appUser[0] = response.get(0);
        });
//        AppUser appUser = signInRepository.getAppUsersList().get(0);
        ViewModel = new ViewModelProvider(this, new PostViewModelFactory(repository)).get(PostDetailsUserViewModel.class);
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ViewModel.deletePost(appUser[0].getUid(), mitem.id)) {
                    System.out.println("delete failed!!!");
                }
                Navigation.findNavController(v).navigate(R.id.action_nav_details_to_user);
            }
        });
    }

}