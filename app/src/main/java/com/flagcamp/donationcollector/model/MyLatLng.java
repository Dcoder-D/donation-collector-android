package com.flagcamp.donationcollector.model;

public class MyLatLng {
    double lat;
    double lng;

    public MyLatLng(String lat, String lng) {
        this.lat = Double.valueOf(lat);
        this.lng = Double.valueOf(lng);
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return "MyLatLng{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
