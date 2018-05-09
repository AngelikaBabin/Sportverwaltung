package com.example.cora.sportverwaltungveranstalter.businesslogic.data;

public class Veranstalter extends Account {
    public Veranstalter(String email, String name, String password) {
        super(email, name, password);
    }

    @Override
    public String toString() {
        return "Veranstalter{" +
                "email='" + getEmail() + '\'' +
                ", name='" + getName() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
