package com.example.cora.sportverwaltung.businesslogic.data;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class Location {
    private int id;
    private String name;
    private double latitude;
    private double longitude;

    public Location(String name, double latitude, double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
