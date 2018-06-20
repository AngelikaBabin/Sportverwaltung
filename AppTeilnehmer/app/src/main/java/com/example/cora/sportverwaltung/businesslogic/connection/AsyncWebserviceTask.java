package com.example.cora.sportverwaltung.businesslogic.connection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @kandut everything
 */

public class AsyncWebserviceTask extends AsyncTask<String, Void, AsyncTaskResult> {
    private static final String BASE_URL = "http://{{ip}}:8080/sportverwaltung/webresources/";
    private AsyncTaskHandler handler;
    private HttpMethod method;
    private URL url;

    private static String accessToken = null;

    public AsyncWebserviceTask(HttpMethod method, String route, AsyncTaskHandler handler, Context context) throws MalformedURLException {
        this.handler = handler;
        this.method = method;
        String ip = PreferenceManager.getDefaultSharedPreferences(context).getString("ip", "192.168.193.150");
        this.url = new URL(BASE_URL.replace("{{ip}}", ip) + route);
    }

    public static void setAccessToken(String accessToken) {
        AsyncWebserviceTask.accessToken = accessToken;
    }

    @Override
    protected AsyncTaskResult doInBackground(String... params) {
        AsyncTaskResult result;
        String queryString = (params.length > 0) ? params[0] : null;
        String jsonString = (params.length > 1) ? params[1] : null;

        try {
            // set querystring
            if (queryString != null) {
                url = new URL(url.toString() + "?" + queryString);
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // set token header if logged in
            if (accessToken != null) {
                connection.setRequestProperty("Token", accessToken);
            }

            // post json
            if (jsonString != null && method == HttpMethod.POST){
                write(connection, method, jsonString);
            }

            if (jsonString == null && method == HttpMethod.DELETE) {
                write(connection, method, "");
            }

            int statusCode = connection.getResponseCode();
            String content = read(connection);

            // set new token if necessary
            if (accessToken == null) {
                accessToken = connection.getHeaderField("Token");
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