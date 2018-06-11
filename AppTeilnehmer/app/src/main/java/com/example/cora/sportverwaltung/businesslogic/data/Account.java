package com.example.cora.sportverwaltung.businesslogic.data;

import android.support.annotation.NonNull;

/**
 * @babin created
 * @kandut small tweaks
 */

public class Account extends Credentials{
    private int id;
    private String name;

    public Account(String email, String name, String password) {
        super(email, password);
        setName(name);
    }

    public Account(String email, String password) {
        super(email, password);
    }

    public Account(int id, String email, String password) {
        this(email, password);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (!getEmail().equals(account.getEmail())) return false;
        if (!getName().equals(account.getName())) return false;
        return getPassword().equals(account.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getEmail().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + getEmail() + '\'' +
                ", name='" + getName() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
