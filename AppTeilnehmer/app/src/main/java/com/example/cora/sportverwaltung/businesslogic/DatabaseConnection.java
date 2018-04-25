package com.example.cora.sportverwaltung.businesslogic;

import com.example.cora.sportverwaltung.businesslogic.data.Account;
import com.google.gson.Gson;

import java.net.NoRouteToHostException;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class DatabaseConnection {
    private static DatabaseConnection connection;
    private final Gson GSON = new Gson();
    private static ControllerSync controller;
    private String url;
    private String userToken;

    public static DatabaseConnection getInstance() {
        if (connection == null)
            connection = new DatabaseConnection("192.168.193.150");
        return connection;
    }

    private DatabaseConnection(String url) {
        this.url = url;
    }

    public String registerTeilnehmer(Account account) throws Exception {
        controller = new ControllerSync(url);

        String stringTeilnehmer = GSON.toJson(account, Account.class);
        String params[] = new String[2];
        params[0] = "REGISTER_TEILNEHMER";
        params[1] = stringTeilnehmer;

        controller.execute(params);

        userToken = controller.get();
        if(userToken.startsWith("Exception"))
            throw new NoRouteToHostException(userToken);
        return userToken;
    }

    public String login(Account account) throws Exception{
        controller = new ControllerSync(url);

        String stringTeilnehmer = GSON.toJson(account, Account.class);
        String params[] = new String[2];
        params[0] = "LOGIN";
        params[1] = stringTeilnehmer;

        controller.execute(params);

        userToken = controller.get();
        return userToken;
    }

    public void logout() throws Exception{
        controller = new ControllerSync(url);

        String params[] = new String[1];
        params[0] = "LOGOUT";

        controller.execute(params);

        controller.get();
    }
}
