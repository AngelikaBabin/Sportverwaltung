package com.example.cora.sportverwaltung.businesslogic.data;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class Veranstalter extends Account {
    public Veranstalter(String email, String name, String password) {
        super(email, name, password);
    }

    @Override
    public String toString() {
        return getName();
    }
}
