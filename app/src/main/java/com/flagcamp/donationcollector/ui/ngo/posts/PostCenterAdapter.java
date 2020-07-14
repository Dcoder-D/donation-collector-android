package com.flagcamp.donationcollector.ui.ngo.posts;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostCenterAdapter extends RecyclerView.Adapter<PostCenterAdapter.PostCenterViewHolder>{

    @NonNull
    @Override
    public PostCenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostCenterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class PostCenterViewHolder extends RecyclerView.ViewHolder {

        public PostCenterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
