package com.example.cora.sportverwaltung.businesslogic.data;

import android.support.annotation.NonNull;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class Account {
    private int id;
    private String email;
    private String name;
    private String password;

    public Account() {

    }

    public Account(int id, String email, String name, String password) {
        setId(id);
        setEmail(email);
        setName(name);
        setPassword(password);
    }

    public Account(String email, String password) {
        this(0, email, "", password);
    }

    public Account(String email, String name, String password) {
        this(0,email, name,password);
    }

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (getId() != account.getId()) return false;
        if (!getEmail().equals(account.getEmail())) return false;
        if (!getName().equals(account.getName())) return false;
        return getPassword().equals(account.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + getId() +
                ", example_email='" + getEmail() + '\'' +
                ", name='" + getName() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
