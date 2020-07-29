package com.flagcamp.donationcollector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostBody {
    @SerializedName("TestText")
    @Expose
    public List<PostItem> TestText;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if(TestText != null) {
            for(PostItem postItem: TestText) {
                sb.append(postItem.toString());
                sb.append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(']');

        return sb.toString();
    }
}
