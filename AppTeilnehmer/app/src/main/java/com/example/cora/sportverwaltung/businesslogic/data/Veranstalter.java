package com.example.cora.sportverwaltung.businesslogic.data;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class Veranstalter extends Account {
    public Veranstalter(int id, String email, String name, String password) {
        super(id, email, name, password);
    }

    public Veranstalter(Account a) {
        this(a.getId(), a.getEmail(), a.getName(), a.getPassword());
    }

    public Veranstalter(){};

    @Override
    public String toString() {
        return "Veranstalter{" +
                "id=" + getId() +
                ", example_email='" + getEmail() + '\'' +
                ", name='" + getName() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
