package com.example.cora.sportverwaltung.businesslogic;

import com.example.cora.sportverwaltung.businesslogic.data.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.NoRouteToHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class DatabaseConnection {
    private static DatabaseConnection DatabaseConnection;
    private final Gson GSON = new Gson();
    private static ControllerSync controller;
    private String url;
    private String userToken;

    public static DatabaseConnection getInstance() {
        if (DatabaseConnection == null)
            DatabaseConnection = new DatabaseConnection("192.168.43.142:8080");
        return DatabaseConnection;
    }

    private DatabaseConnection(String url) {
        this.url = url;
    }

    //Get Example
    public List<Veranstaltung> getVeranstaltungen() throws Exception {
        controller = new ControllerSync(url);
        controller.execute("VERANSTALTUNGEN");

        String stringFromWebService = controller.get();

        Type collectionType = new TypeToken<ArrayList<Veranstaltung>>() {}.getType();

        return GSON.<ArrayList<Veranstaltung>>fromJson(stringFromWebService, collectionType);
    }

    //Post Example
    public String registerTeilnehmer(Teilnehmer teilnehmer) throws Exception {
        controller = new ControllerSync(url);

        String stringTeilnehmer = GSON.toJson(teilnehmer, Teilnehmer.class);
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
    }
}
