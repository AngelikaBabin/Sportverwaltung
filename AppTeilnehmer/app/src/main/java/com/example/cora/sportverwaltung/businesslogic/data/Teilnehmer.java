package com.example.cora.sportverwaltung.businesslogic.data;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class Teilnehmer extends Account{
    private int score;
    //TODO Veranstaltungen

    public Teilnehmer(int score) {
        this.score = score;
    }

    public Teilnehmer(int id, String email, String name, String password, int score) {
        super(id, email, name, password);
        setScore(score);
    }

    public Teilnehmer(Account a, int score) {
        this(a.getId(), a.getEmail(), a.getName(), a.getPassword(), score);
    }

    public Teilnehmer(){}

    public Teilnehmer(String email, String name, String password) {
        this(0,email,name,password,0);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teilnehmer)) return false;
        if (!super.equals(o)) return false;

        Teilnehmer that = (Teilnehmer) o;

        return getScore() == that.getScore();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getScore();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + getId() +
                ", example_email='" + getEmail() + '\'' +
                ", name='" + getName() + '\'' +
                ", password='" + getPassword() + '\'' +
                "score=" + getScore() + '\'' +
                '}';
    }
}
