package com.example.cora.sportverwaltung.businesslogic.data;

import android.support.annotation.NonNull;

/**
 * @babin created
 * @kandut small tweaks
 */

public class Location {
    private String name;
    private double latitude;
    private double longitude;

    public Location(String name, double latitude, double longitude) {
        setName(name);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(@NonNull double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return name;
    }
}
