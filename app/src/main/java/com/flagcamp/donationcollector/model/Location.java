package com.flagcamp.donationcollector.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Location {
    @PrimaryKey
    @NonNull
    public int id;

    public String location;

    public String distance;
}
