package com.example.cora.sportverwaltungveranstalter.businesslogic.data;


import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Veranstalter;

import java.util.Date;
import java.util.Objects;

/**
 * @babin created
 * @kandut small tweaks
 */

public class Veranstaltung {
    private int id;
    private String name;
    private String details;
    private Veranstalter veranstalter;
    private String location;
    private String sportart;
    private Date datetime;
    private int maxTeilnehmer;
    private int countTeilnehmer;

    public Veranstaltung(int id, String name, String details, Veranstalter veranstalter, String location, String sportart, Date datetime, int minTeilnehmer, int maxTeilnehmer, int countTeilnehmer) {
        setName(name);
        setDetails(details);
        setVeranstalter(veranstalter);
        setLocation(location);
        setSportart(sportart);
        setDatetime(datetime);
        setMaxTeilnehmer(maxTeilnehmer);
        setCountTeilnehmer(countTeilnehmer);
    }

    public Veranstaltung(){}

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Veranstalter getVeranstalter() {
        return veranstalter;
    }

    public void setVeranstalter(Veranstalter veranstalter) {
        this.veranstalter = veranstalter;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSportart() {
        return sportart;
    }

    public void setSportart(String sportart) {
        this.sportart = sportart;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getMaxTeilnehmer() {
        return maxTeilnehmer;
    }

    public void setMaxTeilnehmer(int maxTeilnehmer) {
        this.maxTeilnehmer = maxTeilnehmer;
    }

    public int getCountTeilnehmer() {
        return countTeilnehmer;
    }

    public void setCountTeilnehmer(int countTeilnehmer) {
        this.countTeilnehmer = countTeilnehmer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Veranstaltung)) return false;
        Veranstaltung that = (Veranstaltung) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
