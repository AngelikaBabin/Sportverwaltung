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
 * @author Kraschl
 */
@XmlRootElement
public class Event {
    private int id;
    private Account veranstalter;
    private String name;
    private LocalDate datetime;
    private String details;
    private String location;
    private int maxTeilnehmer;
    private String sportart;
    private int countTeilnehmer;

    public Event(int id, Account veranstalter, String name, LocalDate datetime, 
            String details, String location, int max_teilnehmer, String sportart) {
        this.id = id;
        this.veranstalter = veranstalter;
        this.datetime = datetime;
        this.details = details;
        this.location = location;
        this.maxTeilnehmer = max_teilnehmer;
        this.name = name;
        this.sportart = sportart;
    }
    
    public Event(){}

    public int getCountTeilnehmer() {
        return countTeilnehmer;
    }

    public void setCountTeilnehmer(int countTeilnehmer) {
        this.countTeilnehmer = countTeilnehmer;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getVeranstalter() {
        return veranstalter;
    }

    public void setVeranstalter(Account veranstalter) {
        this.veranstalter = veranstalter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getMaxTeilnehmer() {
        return maxTeilnehmer;
    }

    public void setMaxTeilnehmer(int max_teilnehmer) {
        this.maxTeilnehmer = max_teilnehmer;
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
                ", veranstalter=" + veranstalter + 
                ", datetime=" + datetime + ", details=" + details + 
                ", location=" + location + ", max_teilnehmer=" + maxTeilnehmer + ", name=" + name + ", sportart=" + sportart +'}';
    }
}
