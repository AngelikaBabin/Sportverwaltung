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
    private String url_first;
    private static final String url_second = "SportVerwaltung_WebServices/webresources";
    private static String token;

    @Override
    protected String doInBackground(String... params) {
        HttpMethod httpMethod;
        String content;
        URL url;
        String whatToRead = "CONTENT";

        try {
            switch (params[0]) {
                case "LOGIN":
                    url = new URL("http://" + url_first + "/" + url_second + "/login");
                    httpMethod = HttpMethod.POST;
                    whatToRead = "TOKEN";
                    break;
                case "LOGOUT":
                    url = new URL("http://" + url_first + "/" + url_second + "/logout");
                    httpMethod = HttpMethod.POST;
                    whatToRead = "TOKEN";
                    break;
                case "REGISTER_TEILNEHMER":
                    url = new URL("http://" + url_first + "/" + url_second + "/teilnehmer");
                    httpMethod = HttpMethod.POST;
                    whatToRead = "TOKEN";
                    break;
                case "UPDATE_TEILNEHMER":
                    url = new URL("http://" + url_first + "/" + url_second + "/teilnehmer");
                    httpMethod = HttpMethod.PUT;
                    break;
                case "DELETE_TEILNEHMER":
                    url = new URL("http://" + url_first + "/" + url_second + "/teilnehmer");
                    httpMethod = HttpMethod.DELETE;
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

    ControllerSync(String url_first) {
        this.url_first = url_first;
    }

    private String read(HttpURLConnection connection, String whatToRead) throws IOException {
        String result = "NOTHING READ";
        if (whatToRead.equals("CONTENT")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            result = sb.toString();
            reader.close();
        } else if (whatToRead.equals("TOKEN")) {
            result = connection.getHeaderField("token");
            token = result;
        } else if (whatToRead.equals("NOTHING")){
            result = "";
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
