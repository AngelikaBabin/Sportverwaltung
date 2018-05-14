package com.example.cora.sportverwaltung.businesslogic.connection;

import com.example.cora.sportverwaltung.businesslogic.data.Account;
import com.example.cora.sportverwaltung.businesslogic.data.Credentials;
import com.example.cora.sportverwaltung.businesslogic.misc.Filter;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.example.cora.sportverwaltung.businesslogic.misc.AsyncResult;
import com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod;
import com.example.cora.sportverwaltung.businesslogic.misc.ResultType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nicok on 18.04.2018 ^-^.
 */

public class DatabaseConnection {
    private static DatabaseConnection connection;
    private final Gson GSON = new Gson();

    private URL url;

    //Singleton
    public static DatabaseConnection getInstance() {
        if (connection == null) {
            try {
                URL url = new URL("http", "192.168.193.150", 8080, "SportVerwaltung_WebServices/webresources");
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

    public DatabaseConnection() { }

    public String registerTeilnehmer(Account account) throws Exception {
        String accountString = GSON.toJson(account, Account.class);

        String result = get(HttpMethod.POST, "teilnehmer", ResultType.TOKEN, accountString);

        return result;
    }

    public String login(Credentials credentials) throws Exception {
        String accountString = GSON.toJson(credentials, Credentials.class);
        String result = get(HttpMethod.POST, "login", ResultType.TOKEN, accountString);
        return result;
    }

    public String logout() throws Exception {
        String result = get(HttpMethod.POST, "logout", ResultType.STATUS);
        return result;
    }

    public ArrayList<Veranstaltung> getEvents(Filter filter) throws Exception {
        String responseText = get(HttpMethod.GET, "event", ResultType.CONTENT, "filter=" + filter.toString());
        Type collectionType = new TypeToken<ArrayList<Veranstaltung>>() {
        }.getType();
        ArrayList<Veranstaltung> events = GSON.fromJson(responseText, collectionType);

        return events;
    }

    public Veranstaltung getEvent(int eventId) throws Exception {
        String responseText = get(HttpMethod.GET, "event", ResultType.CONTENT, "id=" + eventId);
        Type collectionType = new TypeToken<ArrayList<Veranstaltung>>() {
        }.getType();
        ArrayList<Veranstaltung> events = GSON.fromJson(responseText, collectionType);

        return events.get(0);
    }

    public int participate(final int _eventId, final int _userId) throws Exception {
        String payload = ""; // TODO
        String responseText = get(HttpMethod.POST, "event", ResultType.STATUS, payload);

        return Integer.parseInt(responseText);
    }

    public int deleteAccount() {
        return 500;
    }

    public int updateAccount() {
        return 500;
    }

    private String get(HttpMethod httpMethod, String route, ResultType resultType, String... params) throws Exception {
        ControllerSync controller = new ControllerSync(url);

        ArrayList<String> connectionParams = new ArrayList<>();
        connectionParams.add(httpMethod.toString());
        connectionParams.add(route);
        connectionParams.add(resultType.toString());
        connectionParams.addAll(Arrays.asList(params));

        params = connectionParams.toArray(params);

        controller.execute(params);

        AsyncResult<String> result =  controller.get();
        if(result.getError() != null) {
            throw result.getError();
        }

        return result.getResult();
    }
}
