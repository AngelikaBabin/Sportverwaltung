package com.example.cora.sportverwaltung.businesslogic.data;

import android.support.annotation.NonNull;

/**
 * @babin created
 * @kandut small tweaks
 */

public class Credentials {
    private String email;
    private String password;

    public Credentials(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
