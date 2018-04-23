package com.example.cora.sportverwaltung.businesslogic;

import com.example.cora.sportverwaltung.businesslogic.data.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    public static DatabaseConnection getInstance(){
        if(DatabaseConnection == null)
            DatabaseConnection = new DatabaseConnection();
        return DatabaseConnection;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //Get Example
    public List<Veranstaltung> getVeranstaltungen() throws Exception {
        List<Veranstaltung> result = new ArrayList<>();

        controller = new ControllerSync(url);

        controller.execute("veranstaltungen");
        String stringFromWebService = controller.get();
        System.out.println("[WEBSERVICE] " + stringFromWebService);
        Type collectionType = new TypeToken<ArrayList<Veranstaltung>>() {}.getType();
        ArrayList<Veranstaltung> vec = GSON.fromJson(stringFromWebService, collectionType);

        return vec;
    }

    //Post Example
    public String registerTeilnehmer(Teilnehmer teilnehmer) throws Exception{
        controller = new ControllerSync(url);
        String stringTeilnehmer = GSON.toJson(teilnehmer, Teilnehmer.class);
        System.out.println(teilnehmer + " => " + stringTeilnehmer);
        String params[] = new String[2];
        params[0] = "registerTeilnehmer";
        params[1] = stringTeilnehmer;
        controller.execute(params);
        String stringFromWebService = controller.get();
        System.out.println("[WEBSERVICE] " + stringFromWebService);
        return stringFromWebService;
    }
}
