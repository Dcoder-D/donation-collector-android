package com.flagcamp.donationcollector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NGOUser {
    @SerializedName("ngoName")
    @Expose
    public String ngoName;
    @SerializedName("userId")
    @Expose
    public String userId;
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
