package com.example.cora.sportverwaltungveranstalter.businesslogic.connection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Account;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Credentials;
import com.example.cora.sportverwaltungveranstalter.businesslogic.misc.AsyncResult;
import com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod;
import com.example.cora.sportverwaltungveranstalter.businesslogic.misc.ResultType;
import com.google.gson.Gson;

public class DatabaseConnection {

    private static DatabaseConnection connection;
    private final Gson GSON = new Gson();

    private URL url;

    //Singleton
    public static DatabaseConnection getInstance() {
        if (connection == null) {
            try {
                URL url = new URL("http", "192.168.194.107", 8080, "SportVerwaltung_WebServices/webresources");
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

    public String registerVeranstalter(Account account) throws Exception{
        String strAccount = GSON.toJson(account, Account.class);
        String result = get(HttpMethod.POST, "veranstalter", ResultType.TOKEN, strAccount);
        return result;
    }

    public String login(Credentials credentials) throws Exception{
        String strCredentials = new Gson().toJson(credentials, Credentials.class);
        String result = get(HttpMethod.POST, "login", ResultType.TOKEN, strCredentials);
        return result;
    }

    public String logout() throws Exception{
        String result = get(HttpMethod.POST,"logout", ResultType.STATUS);
        return result;
    }





    private String get(HttpMethod httpMethod, String route, ResultType resType, String... params) throws Exception{
        ControllerSync controller = new ControllerSync(url);
        ArrayList<String> connectionParams = new ArrayList<>();
        connectionParams.add(httpMethod.toString());
        connectionParams.add(route);
        connectionParams.add(resType.toString());
        connectionParams.addAll(Arrays.asList(params));

        String[] parameters = connectionParams.toArray(params);
        controller.execute(params);

        AsyncResult<String> result = controller.get();
        if (result.getError() != null){
            throw result.getError();
        }
        return result.getResult();
    }

}
