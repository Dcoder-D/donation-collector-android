package com.flagcamp.donationcollector.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class Item implements Serializable{
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("urlToImage")
    @Expose
    public String urlToImage;
    @SerializedName("lat")
    @Expose
    public Integer lat;
    @SerializedName("lon")
    @Expose
    public Integer lon;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("posterId")
    @Expose
    public String posterId;
//    String posterName;
    @SerializedName("pickUpNGOId")
    @Expose
    public String pickUpNGOId;
//    String NGOName;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("size")
    @Expose
    public String size;
    @SerializedName("availableTime")
    @Expose
    public List<String> availableTime;
    @SerializedName("status")
    public String status;
    @SerializedName("pickupTime")
    @Expose
    public String pickupTime;


    public static enum Category {
        Apparel, Electronics, Entertainment, Hobbies, Furniture;
    }

    public static enum Status {
        Pending, Scheduled, Collected;
    }

    @NonNull
    @Override
    public String toString() {
        return "Item {" +
                "id='" + id + "'" +
                ", urlToImage='" + urlToImage + "'}";
    }
}
