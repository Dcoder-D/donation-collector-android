package com.flagcamp.donationcollector.ui.user.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flagcamp.donationcollector.R;
import com.flagcamp.donationcollector.model.Item;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PostUserAdapter extends RecyclerView.Adapter<PostUserAdapter.PostUserViewHolder>{

    private List<Item> items = new ArrayList<>();

    @NonNull
    @Override
    public PostUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
        return new PostUserViewHolder(view);
    }

    public void setItems(List<Item> itemList) {
        this.items.clear();
        this.items.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PostUserViewHolder holder, int position) {
        Item item = items.get(position);
        Picasso.get().load(item.urlToImage).into(holder.postImage);
        holder.postCategory.setText(item.category);
        holder.postLocation.setText(item.location);
        holder.postStatus.setText(item.status);
        holder.postStatus.setBackgroundResource(item.status.equals("Pending") ? R.color.light_blue : R.color.light_green);
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public static class PostUserViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postCategory;
        TextView postLocation;
        TextView postStatus;

        public PostUserViewHolder(@NonNull View itemView) {

            super(itemView);
            postImage = itemView.findViewById(R.id.post_card_image);
            postCategory = itemView.findViewById(R.id.post_card_category);
            postLocation = itemView.findViewById(R.id.post_card_location);
            postStatus = itemView.findViewById(R.id.post_card_status);
        }
    }
}
