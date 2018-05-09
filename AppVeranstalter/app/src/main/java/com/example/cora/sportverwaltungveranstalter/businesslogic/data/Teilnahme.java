package com.example.cora.sportverwaltungveranstalter.businesslogic.data;

public class Teilnahme {
    private int startingNumber;
    private int placing;

    public Teilnahme(int startingNumber, int placing) {
        this.startingNumber = startingNumber;
        this.placing = placing;
    }

    public Teilnahme() {
    }

    public int getStartingNumber() {
        return startingNumber;
    }

    public void setStartingNumber(int startingNumber) {
        this.startingNumber = startingNumber;
    }

    public int getPlacing() {
        return placing;
    }

    public void setPlacing(int placing) {
        this.placing = placing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teilnahme)) return false;

        Teilnahme teilnahme = (Teilnahme) o;

        if (getStartingNumber() != teilnahme.getStartingNumber()) return false;
        return getPlacing() == teilnahme.getPlacing();
    }

    @Override
    public int hashCode() {
        int result = getStartingNumber();
        result = 31 * result + getPlacing();
        return result;
    }

    @Override
    public String toString() {
        return "Teilnahme{" +
                "startingNumber=" + startingNumber +
                ", placing=" + placing +
                '}';
    }
}