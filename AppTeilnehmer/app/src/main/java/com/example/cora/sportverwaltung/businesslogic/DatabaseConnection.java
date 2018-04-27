package com.example.cora.sportverwaltung.businesslogic;

import com.example.cora.sportverwaltung.businesslogic.data.Account;
import com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod;
import com.example.cora.sportverwaltung.businesslogic.misc.ResultType;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class DatabaseConnection {
    private static DatabaseConnection connection;
    private final Gson GSON = new Gson();
    private static ControllerSync controller;

    private URL url;

    //Singleton
    public static DatabaseConnection getInstance() {
        if (connection == null) {
            try {
                URL url = new URL("http", "192.168.193.150", "SportVerwaltung_WebServices/webresources");
                connection = new DatabaseConnection(url);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }

    private DatabaseConnection(URL url) {
        this.url = url;
    }

    public String registerTeilnehmer(Account account) throws Exception {
        String accountString = GSON.toJson(account, Account.class);

        String result = get(HttpMethod.POST.toString(),"teilnehmer", ResultType.TOKEN.toString(), accountString);

        if (result.startsWith("ERROR")) //TODO SEMIPROF
            throw new Exception(result);
        return result;
    }

    public String login(Account account) throws Exception {
        String accountString = GSON.toJson(account, Account.class);
        String result = get(HttpMethod.POST.toString(), "login", ResultType.TOKEN.toString(), accountString);

        if (result.startsWith("ERROR")) //TODO SEMIPROF
            throw new Exception(result);
        return result;
    }

    public String logout() throws Exception {
        String result = get(HttpMethod.POST.toString(), "logout", ResultType.NOTHING.toString());

        if (result.startsWith("ERROR")) //TODO SEMIPROF
            throw new Exception(result);
        return result;
    }

    public ArrayList<Veranstaltung> getPastEvents() throws Exception {
        String responseText = get(HttpMethod.GET.toString(), "veranstaltungen", ResultType.CONTENT.toString());

        if (responseText.startsWith("ERROR")) //TODO SEMIPROF
            throw new Exception(responseText);

        Type collectionType = new TypeToken<ArrayList<Veranstaltung>>() {}.getType();
        ArrayList<Veranstaltung> pastEvents = GSON.fromJson(responseText, collectionType);

        return pastEvents;
    }

    private String get(String... params) throws ExecutionException, InterruptedException {
        controller = new ControllerSync(url);
        controller.execute(params);
        return controller.get();
    }
}
