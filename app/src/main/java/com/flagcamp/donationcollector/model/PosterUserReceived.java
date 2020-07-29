package com.flagcamp.donationcollector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PosterUserReceived {
    @SerializedName("userId")
    @Expose
    public String userId;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("userType")
    @Expose
    public String userType;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("address")
    @Expose
    public String address;
}
