package com.flagcamp.donationcollector.ui.ngo.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.flagcamp.donationcollector.R;

import java.util.ArrayList;
import java.util.List;

public class PostDetailsNGOAdapter extends RecyclerView.Adapter<PostDetailsNGOAdapter.PostDetailsNGOViewHolder>{

    private List<String> dates = new ArrayList<>();
    Fragment fragment;
    String status;

    public PostDetailsNGOAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PostDetailsNGOViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_details_card, parent, false);
        return new PostDetailsNGOViewHolder(view);
    }

    public void setDates(String status, List<String> availableTimes) {
        this.status = status;
        this.dates.clear();
        this.dates.addAll(availableTimes);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PostDetailsNGOViewHolder holder, int position) {
        String date = dates.get(position);
        if(status.equals("PENDING")) {
            holder.availableDate.setText(date);
        }else if (status.equals("SCHEDULED")) {
            holder.availableDate.setText(date.substring(0,10));
            holder.datePrefix.setText("Pick-up Date:");
            holder.timePrefix.setText("Pick-up Time:");
            //holder.pickupTime.setText(date.substring(11,16));
        }
    }

    @Override
    public int getItemCount() {

        return dates.size();
    }

    public static class PostDetailsNGOViewHolder extends RecyclerView.ViewHolder {
        TextView datePrefix;
        TextView availableDate;
        TextView timePrefix;
        TextView pickupTime;

        //TextView availableTime;

        public PostDetailsNGOViewHolder(@NonNull View itemView) {

            super(itemView);
            datePrefix = itemView.findViewById(R.id.details_card_date_prefix);
            availableDate = itemView.findViewById(R.id.details_available_date);
            timePrefix = itemView.findViewById(R.id.details_card_time_prefix);
            pickupTime = itemView.findViewById(R.id.details_available_time);
        }
    }
}

