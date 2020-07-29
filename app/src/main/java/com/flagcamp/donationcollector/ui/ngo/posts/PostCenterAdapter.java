package com.flagcamp.donationcollector.ui.ngo.posts;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostCenterAdapter extends RecyclerView.Adapter<PostCenterAdapter.PostCenterViewHolder>{

    private List<Item> items = new ArrayList<>();
    Fragment fragment;
    private Context context;

    public PostCenterAdapter(Fragment fragment, Context context) {
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PostCenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
        return new PostCenterViewHolder(view);
    }

    public void setItems(List<Item> itemList) {
        this.items.clear();
        this.items.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PostCenterViewHolder holder, int position) {
        Item item = items.get(position);

//        Picasso.with(context).load(item.urlToImage).into(holder.postImage);

        Picasso.get().load(item.urlToImage).into(holder.postImage);


        holder.postCategory.setText(item.category);
        holder.postLocation.setText(item.location);
        holder.postStatus.setText(item.status);
        holder.postStatus.setBackgroundResource(item.status.toLowerCase().equals("pending") ? R.color.light_blue : R.color.light_green);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostCenterFragmentDirections.ActionTitlePostcenterToPostDetails actionTitlePostcenterToPostDetails =
                        PostCenterFragmentDirections.actionTitlePostcenterToPostDetails();
                actionTitlePostcenterToPostDetails.setPost(item);
                NavHostFragment.findNavController(fragment).navigate(actionTitlePostcenterToPostDetails);
            }
        });
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public static class PostCenterViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postCategory;
        TextView postLocation;
        TextView postStatus;

        public PostCenterViewHolder(@NonNull View itemView) {

            super(itemView);
            postImage = itemView.findViewById(R.id.post_card_image);
            postCategory = itemView.findViewById(R.id.post_card_category);
            postLocation = itemView.findViewById(R.id.post_card_location);
            postStatus = itemView.findViewById(R.id.post_card_status);
        }
    }
}
