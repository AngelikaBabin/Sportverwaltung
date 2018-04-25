package com.example.cora.sportverwaltung.businesslogic;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.URL;

/**
 * Created by nicok on 18.04.2018 ^-^ ^-^.
 */

public class ControllerSync extends AsyncTask<String, Void, String> {
    private URL url;
    private static String token;

    @Override
    protected String doInBackground(String... params) {
        HttpMethod httpMethod;
        String content;
        String whatToRead = "CONTENT";

        try {
            switch (params[0]) {
                case "LOGIN":
                    url = new URL(url + "/login");
                    httpMethod = HttpMethod.POST;
                    whatToRead = "TOKEN";
                    break;
                case "LOGOUT":
                    url = new URL(url + "/logout");
                    httpMethod = HttpMethod.POST;
                    whatToRead = "TOKEN";
                    break;
                case "REGISTER":
                    url = new URL(url + "/teilnehmer");
                    httpMethod = HttpMethod.POST;
                    whatToRead = "TOKEN";
                    break;
                case "CHANGE_USERDATA":
                    url = new URL(url + "/teilnehmer");
                    httpMethod = HttpMethod.PUT;
                    break;
                case "DELETE":
                    url = new URL(url + "/teilnehmer");
                    httpMethod = HttpMethod.DELETE;
                    break;
                case "EVENT_ANMELDEN":
                    url = new URL(url + "/teilnahme");
                    httpMethod = HttpMethod.POST;
                    break;
                case "EVENT_ABMELDEN":
                    url = new URL(url + "/teilnahme");
                    httpMethod = HttpMethod.DELETE;
                    break;
                case "FUTURE_EVENTS":
                    url = new URL(url + "/teilnehmer");
                    httpMethod = HttpMethod.GET;
                    break;
                case "CURRENT_EVENTS":
                    url = new URL(url + "/teilnehmer");
                    httpMethod = HttpMethod.GET;
                    break;
                case "PAST_EVENTS":
                    url = new URL(url + "/teilnehmer");
                    httpMethod = HttpMethod.GET;
                    break;
                default:
                    throw new Exception("invalid route identifier");
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if(token != null) {
                connection.setRequestProperty("Token", token);
            }

            if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT || httpMethod == HttpMethod.DELETE) {
                    write(connection, params);
            }

            content = read(connection, whatToRead);

            connection.disconnect();
        } catch(NoRouteToHostException ex){
          content = "Exception: make sure you are connected to the internet";
        } catch (Exception ex) {
            content = "Exception in doInBackground: " + ex.getMessage();
        }

        return content;
    }

    public ControllerSync(URL url) {
        this.url = url;
    }

    private String read(HttpURLConnection connection, String whatToRead) throws IOException {
        String result = "NOTHING READ";
        switch (whatToRead) {
            case "CONTENT":
                BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                result = sb.toString();
                reader.close();
                break;
            case "TOKEN":
                result = connection.getHeaderField("token");
                token = result;
                break;
            case "NOTHING":
                result = "";
                break;
        }

        return result;
    }

    private void write(HttpURLConnection connection, String[] params) throws IOException {
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        writer.write(params[1]);
        writer.flush();
        writer.close();

        connection.getResponseCode();
    }
}
