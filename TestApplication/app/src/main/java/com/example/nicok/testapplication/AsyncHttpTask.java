package com.example.nicok.testapplication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nicok on 18.04.2018 ^-^ ^-^.
 */

public class AsyncHttpTask extends AsyncTask<String, Void, AsyncTaskResult<String>> {
    private final AsyncTaskHandler handler;

    public AsyncHttpTask(AsyncTaskHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        handler.onPreExecute();
    }

    @Override
    protected AsyncTaskResult<String> doInBackground(String... params) {
        AsyncTaskResult<String> result;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String content = read(connection);
            result = new AsyncTaskResult<>(content);

            connection.disconnect();
        } catch (Exception ex) {
            result = new AsyncTaskResult<>(ex);
            onCancelled(result);
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(AsyncTaskResult<String> result) {
        handler.onError(result.getError());
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<String> result) {
        handler.onSuccess(result.getResult());
    }

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
}

