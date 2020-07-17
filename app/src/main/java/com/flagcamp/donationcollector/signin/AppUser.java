package com.flagcamp.donationcollector.signin;

import java.util.Objects;

public class AppUser {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phone;
    private String organizationName;
    private boolean user;

    public AppUser() {
        // Default constructor required for calls to DataSnapshot.getValue(AppUser.class)
    }

    public AppUser(String emailAddress, String phone, boolean user) {
        this.firstName = "";
        this.lastName = "";
        this.emailAddress = emailAddress;
        this.phone = phone;
        this.user = user;
        this.organizationName = "";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public AppUser(AppUser otherUser) {
        this.firstName = otherUser.firstName;
        this.lastName = otherUser.lastName;
        this.emailAddress = otherUser.emailAddress;
        this.organizationName = otherUser.organizationName;
        this.phone = otherUser.phone;
        this.user = otherUser.user;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public boolean isUser() {
        return user;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return user == appUser.user &&
                Objects.equals(firstName, appUser.firstName) &&
                Objects.equals(lastName, appUser.lastName) &&
                Objects.equals(emailAddress, appUser.emailAddress) &&
                Objects.equals(phone, appUser.phone) &&
                Objects.equals(organizationName, appUser.organizationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, emailAddress, phone, organizationName, user);
    }
}
