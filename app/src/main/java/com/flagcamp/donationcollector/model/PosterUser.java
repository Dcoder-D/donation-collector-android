package com.flagcamp.donationcollector.model;

import com.flagcamp.donationcollector.signin.AppUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PosterUser {
    @SerializedName("userId")
    @Expose
    public String userId;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("ngoName")
    @Expose
    public String ngoName;
    @SerializedName("userType")
    @Expose
    public String userType;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("address")
    @Expose
    public String address;

    public PosterUser(String userId, String firstName, String ngoName, String userType, String email, String address) {
        this.userId = userId;
        this.firstName = firstName;
        this.userType = userType;
        this.email = email;
        this.address = address;
    }

    public PosterUser(String firstName, String lastName, String userId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PosterUser(AppUser appUser) {
        this.userId = appUser.getUid();
        this.firstName = appUser.getFirstName();
        this.lastName =appUser.getLastName();
        this.ngoName = "";
        this.userType = "INDIVIDUAL";
        this.email = appUser.getEmailAddress();
        this.address = "";
    }

    @Override
    public String toString() {
        return "\"posterUser\":{" +
                "\"userId\":\"" + userId + '\"' +
                ", \"firstName\":\"" + firstName + '\"' +
                ", \"lastName\":\"" + lastName + '\"' +
                ", \"ngoName\":\"" + ngoName + '\"' +
                ", \"userType\":\"" + userType + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"address\":\"" + address + '\"' +
                '}';
    }
}
