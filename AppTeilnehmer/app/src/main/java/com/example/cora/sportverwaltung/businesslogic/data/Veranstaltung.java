package com.example.cora.sportverwaltung.businesslogic.data;


import java.time.LocalDate;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class Veranstaltung {
    private int id;
    private String name;
    private String details;
    private Veranstalter veranstalter;
    private Location location;
    private Sportart sportart;
    private LocalDate datetime;
    private int minTeilnehmer;
    private int maxTeilnehmer;

    public Veranstaltung(int id, String name, String details, Veranstalter veranstalter, Location location, Sportart sportart, LocalDate datetime, int minTeilnehmer, int maxTeilnehmer) {
        setId(id);
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

        if (getId() != that.getId()) return false;
        if (getMinTeilnehmer() != that.getMinTeilnehmer()) return false;
        if (getMaxTeilnehmer() != that.getMaxTeilnehmer()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getDetails() != null ? !getDetails().equals(that.getDetails()) : that.getDetails() != null)
            return false;
        if (getVeranstalter() != null ? !getVeranstalter().equals(that.getVeranstalter()) : that.getVeranstalter() != null)
            return false;
        if (getLocation() != null ? !getLocation().equals(that.getLocation()) : that.getLocation() != null)
            return false;
        if (getSportart() != that.getSportart()) return false;
        return getDatetime() != null ? getDatetime().equals(that.getDatetime()) : that.getDatetime() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDetails() != null ? getDetails().hashCode() : 0);
        result = 31 * result + (getVeranstalter() != null ? getVeranstalter().hashCode() : 0);
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        result = 31 * result + (getSportart() != null ? getSportart().hashCode() : 0);
        result = 31 * result + (getDatetime() != null ? getDatetime().hashCode() : 0);
        result = 31 * result + getMinTeilnehmer();
        result = 31 * result + getMaxTeilnehmer();
        return result;
    }

    @Override
    public String toString() {
        return "Veranstaltung{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
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
