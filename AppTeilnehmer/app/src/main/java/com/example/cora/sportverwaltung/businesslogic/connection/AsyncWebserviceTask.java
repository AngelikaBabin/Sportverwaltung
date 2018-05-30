package com.example.cora.sportverwaltung.businesslogic.connection;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod;
import com.google.gson.JsonObject;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by nicok on 28.05.2018 ^-^.
 */
public class AsyncWebserviceTask extends AsyncTask<String, Void, AsyncTaskResult> {
    private static final String BASE_URL = "http://192.168.193.150:8080/SportVerwaltung_WebServices/webresources/";
    private AsyncTaskHandler handler;
    private HttpMethod method;
    private URL url;

    public AsyncWebserviceTask(HttpMethod method, String route, AsyncTaskHandler handler) throws MalformedURLException {
        this.handler = handler;
        this.method = method;
        this.url = new URL(BASE_URL + route);
    }

    @Override
    protected AsyncTaskResult doInBackground(String... params) {
        AsyncTaskResult result;

        try {
            // set querystring
            if (params.length != 0 && method == HttpMethod.GET) {
               url = new URL(url.toString() +"?" + params[0]);
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // set token header if logged in
            SharedPreferences preferences = null;
            if(handler instanceof Activity) {
                preferences = PreferenceManager.getDefaultSharedPreferences(((Activity) handler).getApplicationContext());
            } else if(handler instanceof Fragment) {
                preferences = PreferenceManager.getDefaultSharedPreferences(((Fragment) handler).getActivity().getApplicationContext());
            }
            String token = null;
            if (preferences != null) {
                token = preferences.getString("TOKEN", null);
            }

            if (token != null) {
                connection.setRequestProperty("Token", token);
            }

            if (params.length != 0 && method == HttpMethod.POST) {
                write(connection, method, params[0]);
            }

            int statusCode = connection.getResponseCode();
            String content = read(connection);

            // set new token if necessary
            if (preferences.getString("TOKEN", null) == null) {
                preferences.edit().putString("TOKEN", connection.getHeaderField("Token")).apply();

            }

            result = new AsyncTaskResult(statusCode, content);

            connection.disconnect();
        } catch (Exception ex) {
            result = new AsyncTaskResult(ex);
            onCancelled(result);
        }
        return result;
    }

    //region LISTENER
    @Override
    protected void onPreExecute() {
        handler.onPreExecute();
    }

    @Override
    protected void onCancelled(AsyncTaskResult result) {
        handler.onError(result.getError());
    }

    @Override
    protected void onCancelled() {
        handler.onError(null);
    }

    @Override
    protected void onPostExecute(AsyncTaskResult result) {
        handler.onSuccess(result.getStatusCode(), result.getContent());
    }

    // endregion
    private String read(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        reader.close();
        return sb.toString();
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
}