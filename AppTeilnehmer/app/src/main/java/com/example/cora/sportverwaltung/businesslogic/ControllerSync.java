package com.example.cora.sportverwaltung.businesslogic;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by nicok on 18.04.2018 ^-^ ^-^.
 */

public class ControllerSync extends AsyncTask<String, Void, String> {
    private String url_first;
    private static final String url_second = "/SportVerwaltung/webresources";

    @Override
    protected String doInBackground(String... params) {
        boolean isGet = true, isPut = false;

        BufferedReader reader = null;
        BufferedWriter writer = null;
        String content = null;
        URL url = null;

        try{
            if(params[0].equals("REGISTER_VERANSTALTER")) {
                url = new URL(url_first + url_second + "registerVeranstalter");
            } else if(params[0].equals("REGISTER_TEILNEHMER")) {
                url = new URL(url_first + url_second + "registerTeilnehmer");
            }

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            if(isPut) {
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write(params[1]);
                writer.flush();
                writer.close();
                connection.getResponseCode();
            }

            reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null) {
                sb.append(line);
            }

            content = sb.toString();
            reader.close();
            connection.disconnect();
        } catch(Exception ex){
            content = "Error in doInBackground: " + ex.getMessage();
        }

        return content;
    }

    public ControllerSync(String url_first) throws Exception{
        this.url_first = url_first;
    }
}
