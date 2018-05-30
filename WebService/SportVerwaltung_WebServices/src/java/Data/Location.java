/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chris
 */
@XmlRootElement
public class Location {
    private int id;
    private String Name;
    private double latitude;
    private double longitude;

    public Location(int id, String Name, double latitude, double longitude) {
        this.id = id;
        this.Name = Name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Location(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", Name=" + Name + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}