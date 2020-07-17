package com.flagcamp.donationcollector.ui.user.posts;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostUserAdapter extends RecyclerView.Adapter<PostUserAdapter.PostUserViewHolder>{

    @NonNull
    @Override
    public PostUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostUserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class PostUserViewHolder extends RecyclerView.ViewHolder {

        public PostUserViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
