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

    public PostDetailsNGOAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PostDetailsNGOViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_details_card, parent, false);
        return new PostDetailsNGOViewHolder(view);
    }

    public void setDates(List<String> availableTimes) {
        this.dates.clear();
        this.dates.addAll(availableTimes);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PostDetailsNGOViewHolder holder, int position) {
        String date = dates.get(position);
        holder.availableDate.setText(date);
    }

    @Override
    public int getItemCount() {

        return dates.size();
    }

    public static class PostDetailsNGOViewHolder extends RecyclerView.ViewHolder {
        TextView availableDate;
        //TextView availableTime;

        public PostDetailsNGOViewHolder(@NonNull View itemView) {

            super(itemView);
            availableDate = itemView.findViewById(R.id.details_available_date);
        }
    }
}

