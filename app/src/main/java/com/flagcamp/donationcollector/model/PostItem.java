package com.flagcamp.donationcollector.model;


import com.flagcamp.donationcollector.signin.AppUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostItem {
    @SerializedName("posterUser")
    @Expose
    PosterUser posterUser;
//    @SerializedName("urlToImage")
//    @Expose
//    String urlToImage;
    @SerializedName("itemName")
    @Expose
    String itemName;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("category")
    @Expose
    String category;
    @SerializedName("size")
    @Expose
    String size;
    @SerializedName("schedule")
    @Expose
    List<String> schedule;
    @SerializedName("location")
    @Expose
    String location;
    @SerializedName("lat")
    @Expose
    String lat;
    @SerializedName("lon")
    @Expose
    String lon;
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("pickUpdate")
    @Expose
    String pickUpDate;

    public PostItem(Item item, AppUser appUser) {
        posterUser = new PosterUser(appUser);
//        urlToImage = "";
        itemName = "placeHolder";
        description = item.description;
        category = item.category.toUpperCase();
        size = "SMALL";
        schedule = item.availableTime;
        location = item.location;
        lat = "";
        lon ="";
        status = "PENDING";
        pickUpDate = "";
    }

    @Override
    public String toString() {
        return "{" +
                posterUser.toString() +
                ", \"itemName\":\"" + itemName + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"category\":\"" + category + '\"' +
                ", \"size\":\"" + size + '\"' +
                ", \"schedule\":" + listToString(schedule) +
                ", \"location\":\"" + location + '\"' +
                ", \"lat\":\"" + lat + '\"' +
                ", \"lon\":\"" + lon + '\"' +
                ", \"status\":\"" + status + '\"' +
                ", \"pickUpDate\":\"" + pickUpDate + '\"' +
                '}';
    }

    private static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if(list != null) {
            for(String s: list) {
                sb.append('"');
                sb.append(s);
                sb.append("\",");
            }
            // Remove the extra , in the end
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(']');
        return sb.toString();
    }
}
