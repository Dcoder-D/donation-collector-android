package com.flagcamp.donationcollector.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item implements Serializable{
    @NonNull
    @PrimaryKey
    @SerializedName("itemId")
    @Expose
    public String id;
    @SerializedName("urlToImage")
    @Expose
    public String urlToImage;
    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("NGOUser")
    @Expose
    public NGOUser ngoUser;
    @SerializedName("postUser")
    @Expose
    public PosterUserReceived posterUser;
    @SerializedName("address")
    @Expose
    public String location;
//    @SerializedName("posterId")
//    @Expose
//    public String posterId;
//    String posterName;
//    @SerializedName("pickUpNGOId")
//    @Expose
//    public String pickUpNGOId;
////    String NGOName;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("itemName")
    @Expose
    public String itemName;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("size")
    @Expose
    public String size;
    @SerializedName("schedule")
    @Expose
    public List<String> availableTime;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("pickUpDate")
    @Expose
    public String pickupTime;
    @SerializedName("pickUpDate")
    @Expose
    public String pickUpDate;

    public enum Category {
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
