package com.example.cora.sportverwaltung.businesslogic;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nicok on 18.04.2018 ^-^ ^-^.
 */

public class ControllerSync extends AsyncTask<String, Void, String> {
    private String url_first;
    private static final String url_second = "/SportVerwaltung_WebServices/webresources";

    @Override
    protected String doInBackground(String... params) {
        HttpMethod httpMethod = null;
        String content = null;
        URL url = null;
        String whatToRead = "CONTENT";

        try {
            if (params[0].equals("LOGIN_TEILNEHMER")) {
                url = new URL("http://" + url_first + url_second + "/teilnehmer");
                httpMethod = HttpMethod.GET;
                whatToRead = "TOKEN";
            } else if (params[0].equals("REGISTER_TEILNEHMER")) {
                url = new URL("http://" + url_first + url_second + "/teilnehmer");
                httpMethod = HttpMethod.POST;
                whatToRead = "TOKEN";
            } else if (params[0].equals("UPDATE_TEILNEHMER")) {
                url = new URL("http://" + url_first + url_second + "/teilnehmer");
                httpMethod = HttpMethod.PUT;
            } else if (params[0].equals("DELETE_TEILNEHMER")) {
                url = new URL("http://" + url_first + url_second + "/teilnehmer");
                httpMethod = HttpMethod.DELETE;
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT || httpMethod == HttpMethod.DELETE) {
                write(connection, params);
            }

            content = read(connection, whatToRead);

            connection.disconnect();
        } catch (Exception ex) {
            content = "Error in doInBackground: " + ex.getMessage();
        }

        return content;
    }

    public ControllerSync(String url_first) throws Exception {
        this.url_first = url_first;
    }

    private String read(HttpURLConnection connection, String whatToRead) throws IOException {
        String result = "NOT READ CORRECTLY";
        if(whatToRead == "CONTENT") {
        BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        result = sb.toString();
        reader.close();
        } else if(whatToRead == "TOKEN") {
            result = connection.getHeaderField("token");
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
