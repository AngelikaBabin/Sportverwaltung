/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chris
 */
@XmlRootElement
public class Event {
    private int id;
    private int id_veranstalter;
    private String name;
    private LocalDate datetime;
    private String details;
    private String location;
    private int max_teilnehmer;
    private int min_teilnehmer;
    private String sportart;

    public Event(int id, int id_veranstalter, String name, LocalDate datetime, 
            String details, String location, int max_teilnehmer, int min_teilnehmer, String sportart) {
        this.id = id;
        this.id_veranstalter = id_veranstalter;
        this.datetime = datetime;
        this.details = details;
        this.location = location;
        this.max_teilnehmer = max_teilnehmer;
        this.min_teilnehmer = min_teilnehmer;
        this.name = name;
        this.sportart = sportart;
    }

    Event(int aInt, int aInt0, LocalDate toLocalDate, String string, String string0, int aInt1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_veranstalter() {
        return id_veranstalter;
    }

    public void setId_veranstalter(int id_veranstalter) {
        this.id_veranstalter = id_veranstalter;
    }

    public LocalDate getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDate datetime) {
        this.datetime = datetime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMax_teilnehmer() {
        return max_teilnehmer;
    }

    public void setMax_teilnehmer(int max_teilnehmer) {
        this.max_teilnehmer = max_teilnehmer;
    }

    public int getMin_teilnehmer() {
        return min_teilnehmer;
    }

    public void setMin_teilnehmer(int min_teilnehmer) {
        this.min_teilnehmer = min_teilnehmer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public String getSportart() {
        return sportart;
    }

    public void setSportart(String sportart) {
        this.sportart = sportart;
    }

    @Override
    public String toString() {
        return "Veranstaltung{" + "id=" + id + 
                ", id_veranstalter=" + id_veranstalter + 
                ", datetime=" + datetime + ", details=" + details + 
                ", location=" + location + ", max_teilnehmer=" + max_teilnehmer + 
                ", min_teilnehmer=" + min_teilnehmer + ", name=" + name + ", sportart=" + sportart +'}';
    }
}
