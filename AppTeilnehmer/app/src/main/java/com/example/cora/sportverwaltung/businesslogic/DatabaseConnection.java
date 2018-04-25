package com.example.cora.sportverwaltung.businesslogic;

import com.example.cora.sportverwaltung.businesslogic.data.Account;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.URL;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class DatabaseConnection {
    private static DatabaseConnection connection;
    private final Gson GSON = new Gson();
    private static ControllerSync controller;

    private URL url;

    private String userToken;

    public static DatabaseConnection getInstance() {
        if (connection == null) {
            URL url = null;
            try {
                url = new URL("http", "192.168.193.150", "SportVerwaltung_WebServices/webresources");
                connection = new DatabaseConnection(url);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }

    private DatabaseConnection(URL url) {
        this.url = url;
        controller = new ControllerSync(url);
    }

    public String registerTeilnehmer(Account account) throws Exception {
        controller = new ControllerSync(url);

        String stringTeilnehmer = GSON.toJson(account, Account.class);
        String params[] = new String[2];
        params[0] = "REGISTER";
        params[1] = stringTeilnehmer;

        controller.execute(params);

        userToken = controller.get();
        if (userToken.startsWith("Exception"))
            throw new NoRouteToHostException(userToken);
        return userToken;
    }

    public String login(Account account) throws Exception {
        controller = new ControllerSync(url);

        String stringTeilnehmer = GSON.toJson(account, Account.class);
        String params[] = new String[2];
        params[0] = "LOGIN";
        params[1] = stringTeilnehmer;

        controller.execute(params);

        userToken = controller.get();
        return userToken;
    }

    public void logout() throws Exception {
        controller = new ControllerSync(url);

        String params[] = new String[1];
        params[0] = "LOGOUT";

        controller.execute(params);

        controller.get();
    }
}
