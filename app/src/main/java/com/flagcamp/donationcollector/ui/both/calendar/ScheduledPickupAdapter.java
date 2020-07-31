package com.flagcamp.donationcollector.ui.both.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.model.Item;
import com.flagcamp.donationcollector.ui.ngo.calendar.CalendarNGOFragment;
import com.flagcamp.donationcollector.ui.ngo.calendar.CalendarNGOFragmentDirections;
import com.flagcamp.donationcollector.ui.ngo.calendar.NGOScheduledPickupFragment;
import com.flagcamp.donationcollector.ui.ngo.calendar.NGOScheduledPickupFragmentDirections;
import com.flagcamp.donationcollector.ui.ngo.posts.PostCenterAdapter;
import com.flagcamp.donationcollector.ui.user.calendar.CalendarUserFragment;
import com.flagcamp.donationcollector.ui.user.calendar.UserScheduledPickupFragment;
import com.flagcamp.donationcollector.ui.user.posts.PostDetailsUserFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ScheduledPickupAdapter extends RecyclerView.Adapter<ScheduledPickupAdapter.ScheduledPickupViewHolder> {

    private List<Item> items = new ArrayList<>();
    Fragment fragment;

    public ScheduledPickupAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ScheduledPickupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
        return new ScheduledPickupAdapter.ScheduledPickupViewHolder(view);
    }

    public void setItems(List<Item> itemList) {
        this.items.clear();
        this.items.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduledPickupViewHolder holder, int position) {
        Item item = items.get(position);
        Picasso.get().load(item.urlToImage).into(holder.postImage);
        holder.postCategory.setText(item.category);
        holder.postLocation.setText(item.location);
        holder.postStatus.setText(item.status);
        holder.postStatus.setBackgroundResource(item.status.toLowerCase().equals("pending") ?
                R.color.light_blue : R.color.light_green);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(fragment.getClass().isAssignableFrom(NGOScheduledPickupFragment.class)) {
                    NGOScheduledPickupFragmentDirections.ActionTitleScheduledpickupToPostDetails actionTitleScheduledpickupToPostDetails =
                            NGOScheduledPickupFragmentDirections.actionTitleScheduledpickupToPostDetails();
                    actionTitleScheduledpickupToPostDetails.setPost(item);
                    NavHostFragment.findNavController(fragment).navigate(actionTitleScheduledpickupToPostDetails);
                } else if(fragment.getClass().isAssignableFrom(CalendarNGOFragment.class)) {
                    CalendarNGOFragmentDirections.ActionTitleCalendarngoToPostDetails actionTitleCalendarngoToPostDetails =
                            CalendarNGOFragmentDirections.actionTitleCalendarngoToPostDetails();
                    actionTitleCalendarngoToPostDetails.setPost(item);
                    NavHostFragment.findNavController(fragment).navigate(actionTitleCalendarngoToPostDetails);
                } else if(fragment.getClass().isAssignableFrom(CalendarUserFragment.class)) {
                    PostDetailsUserFragment.setItem(item);
                    NavHostFragment.findNavController(fragment).navigate(R.id.action_title_calendaruser_to_post_details);
                } else  if(fragment.getClass().isAssignableFrom(UserScheduledPickupFragment.class)) {
                    PostDetailsUserFragment.setItem(item);
                    NavHostFragment.findNavController(fragment).navigate(R.id.action_title_scheduledpickup_user_to_post_details);
                };
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ScheduledPickupViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postCategory;
        TextView postLocation;
        TextView postStatus;

        public ScheduledPickupViewHolder(@NonNull View itemView) {

            super(itemView);
            postImage = itemView.findViewById(R.id.post_card_image);
            postCategory = itemView.findViewById(R.id.post_card_category);
            postLocation = itemView.findViewById(R.id.post_card_location);
            postStatus = itemView.findViewById(R.id.post_card_status);
        }

    }

    //date=date
}

