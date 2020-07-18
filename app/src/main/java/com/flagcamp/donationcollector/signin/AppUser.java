package com.flagcamp.donationcollector.signin;

import java.io.Serializable;
import java.util.Objects;

public class AppUser implements Serializable {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phone;
    private String organizationName;
    private String uid;
    private boolean isUser;

    public AppUser() {
        // Default constructor required for calls to DataSnapshot.getValue(AppUser.class)
    }

    public static class AppUserBuilder {
        private String firstName = "";
        private String lastName = "";
        private String emailAddress = "";
        private String phone = "";
        private String organizationName = "";
        private String uid;
        private boolean isUser;

        public AppUserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AppUserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AppUserBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public AppUserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public AppUserBuilder organizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        public AppUserBuilder uid(String uid) {
            this.uid = uid;
            return this;
        }

        public AppUserBuilder isUser(boolean isUser) {
            this.isUser = isUser;
            return this;
        }

        public AppUser build() {
            return new AppUser(this);
        }
    }

    private AppUser(AppUserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.emailAddress = builder.emailAddress;
        this.phone = builder.phone;
        this.isUser = builder.isUser;
        this.organizationName = builder.organizationName;
        this.uid = builder.uid;
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

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUser(boolean isUser) {
        this.isUser = isUser;
    }

    public AppUser(AppUser otherUser) {
        this.firstName = otherUser.firstName;
        this.lastName = otherUser.lastName;
        this.emailAddress = otherUser.emailAddress;
        this.organizationName = otherUser.organizationName;
        this.phone = otherUser.phone;
        this.isUser = otherUser.isUser;
        this.uid = otherUser.uid;
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

    public String getUid() {
        return uid;
    }

    public boolean isUser() {
        return isUser;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", uid='" + uid + '\'' +
                ", isUser=" + isUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return isUser == appUser.isUser &&
                Objects.equals(firstName, appUser.firstName) &&
                Objects.equals(lastName, appUser.lastName) &&
                Objects.equals(emailAddress, appUser.emailAddress) &&
                Objects.equals(phone, appUser.phone) &&
                Objects.equals(organizationName, appUser.organizationName) &&
                Objects.equals(uid, appUser.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, emailAddress, phone, organizationName, uid, isUser);
    }
}