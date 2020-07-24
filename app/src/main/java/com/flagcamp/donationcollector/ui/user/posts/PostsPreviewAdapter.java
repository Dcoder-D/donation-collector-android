package com.flagcamp.donationcollector.ui.user.posts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.LinkedList;
import java.util.List;

public class PostsPreviewAdapter extends RecyclerView.Adapter<PostsPreviewAdapter.PostsPreviewHolder> {

    private List<Item> items = new LinkedList<>();


    @NonNull
    @Override
    public PostsPreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.added_item, parent, false);
        return new PostsPreviewHolder(view);
    }

    public void setItems(List<Item> itemList) {
        this.items.clear();
        this.items.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PostsPreviewHolder holder, int position) {
        Item item = items.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(item.urlToImage);
        holder.postImage.setImageBitmap(bitmap);
//        Picasso.get().load(item.urlToImage).into(holder.postImage);
        holder.postCategory.setText(item.category);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class PostsPreviewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postCategory;

        public PostsPreviewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.added_image);
            postCategory = itemView.findViewById(R.id.added_image_text);
        }
    }
}
