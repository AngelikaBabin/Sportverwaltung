package com.example.cora.sportverwaltung.businesslogic.connection;

import android.os.AsyncTask;

import com.example.cora.sportverwaltung.businesslogic.misc.AsyncResult;
import com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod;
import com.example.cora.sportverwaltung.businesslogic.misc.ResultType;

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

public class ControllerSync extends AsyncTask<String, Void, AsyncResult<String>> {
    private URL url;
    private static String token;

    ControllerSync(URL url) {
        this.url = url;
    }

    @Override
    protected AsyncResult<String> doInBackground(String... params) {
        AsyncResult<String> result;

        // extract request params for clarity
        HttpMethod httpMethod = HttpMethod.valueOf(params[0]);
        String route = params[1];
        String whatToRead = params[2];
        String payload = (params.length >= 4) ? params[3] : null;

        try {
            // set payload or querystring
            if(payload != null && httpMethod == HttpMethod.GET){
                route += "?" + payload;
            }

            url = new URL(url + "/" + route);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // set token header if logged in
            if (token != null) {
                connection.setRequestProperty("Token", token);
            }

            if(payload != null && httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT || httpMethod == HttpMethod.DELETE) {
                write(connection, httpMethod, payload);
            }

            result = new AsyncResult<>(read(connection, ResultType.valueOf(whatToRead)));

            connection.disconnect();

        } catch (Exception ex) {
            result = new AsyncResult<>(ex);
        }

        return result;
    }

    private void write(HttpURLConnection connection, HttpMethod httpMethod, String json) throws IOException {
        connection.setRequestMethod(httpMethod.toString());
        connection.setRequestProperty("Content-Type", "application/json");

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        writer.write(json);
        writer.flush();
        writer.close();

        connection.getResponseCode();
    }

    private String read(HttpURLConnection connection, ResultType resultType) throws IOException {
        String result = null;
        switch (resultType) {
            case CONTENT:
                // read responseText
                BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                result = sb.toString();
                reader.close();
                break;
            case TOKEN:
                // read token header
                result = connection.getHeaderField("Token");
                token = result;
                break;
            case STATUS:
                result = String.valueOf(connection.getResponseCode());
                break;
        }

        return result;
    }
}
