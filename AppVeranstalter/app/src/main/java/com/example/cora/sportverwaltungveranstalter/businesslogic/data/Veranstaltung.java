package com.example.cora.sportverwaltungveranstalter.businesslogic.data;

import java.time.LocalDate;
import java.util.Objects;

public class Veranstaltung {
    private String name;
    private String details;
    private Veranstalter veranstalter;
    private Location location;
    private Sportart sportart;
    private LocalDate datetime;
    private int minTeilnehmer;
    private int maxTeilnehmer;

    public Veranstaltung(int id, String name, String details, Veranstalter veranstalter, Location location, Sportart sportart, LocalDate datetime, int minTeilnehmer, int maxTeilnehmer) {
        setName(name);
        setDetails(details);
        setVeranstalter(veranstalter);
        setLocation(location);
        setSportart(sportart);
        setDatetime(datetime);
        setMinTeilnehmer(minTeilnehmer);
        setMaxTeilnehmer(maxTeilnehmer);
    }

    public Veranstaltung(){}

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Sportart getSportart() {
        return sportart;
    }

    public void setSportart(Sportart sportart) {
        this.sportart = sportart;
    }

    public LocalDate getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDate datetime) {
        this.datetime = datetime;
    }

    public int getMinTeilnehmer() {
        return minTeilnehmer;
    }

    public void setMinTeilnehmer(int minTeilnehmer) {
        this.minTeilnehmer = minTeilnehmer;
    }

    public int getMaxTeilnehmer() {
        return maxTeilnehmer;
    }

    public void setMaxTeilnehmer(int maxTeilnehmer) {
        this.maxTeilnehmer = maxTeilnehmer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Veranstaltung)) return false;
        Veranstaltung that = (Veranstaltung) o;
        return getMinTeilnehmer() == that.getMinTeilnehmer() &&
                getMaxTeilnehmer() == that.getMaxTeilnehmer() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDetails(), that.getDetails()) &&
                Objects.equals(getVeranstalter(), that.getVeranstalter()) &&
                Objects.equals(getLocation(), that.getLocation()) &&
                getSportart() == that.getSportart() &&
                Objects.equals(getDatetime(), that.getDatetime());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getDetails(), getVeranstalter(), getLocation(), getSportart(), getDatetime(), getMinTeilnehmer(), getMaxTeilnehmer());
    }

    @Override
    public String toString() {
        return "Veranstaltung{" +
                "name='" + getName() + '\'' +
                ", details='" + getDetails() + '\'' +
                ", veranstalter=" + getVeranstalter() +
                ", location=" + getLocation() +
                ", sportart=" + getSportart() +
                ", datetime=" + getDatetime() +
                ", minTeilnehmer=" + getMinTeilnehmer() +
                ", maxTeilnehmer=" + getMaxTeilnehmer() +
                '}';
    }
}