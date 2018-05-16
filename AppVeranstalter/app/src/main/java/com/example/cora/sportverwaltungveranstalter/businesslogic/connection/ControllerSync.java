package com.example.cora.sportverwaltungveranstalter.businesslogic.connection;

import android.os.AsyncTask;

import com.example.cora.sportverwaltungveranstalter.businesslogic.misc.AsyncResult;
import com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod;
import com.example.cora.sportverwaltungveranstalter.businesslogic.misc.ResultType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.ResultType.CONTENT;
import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.ResultType.STATUS;
import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.ResultType.TOKEN;

public class ControllerSync extends AsyncTask <String, Void, AsyncResult<String>>{
    private URL url;
    private static String token;

    ControllerSync(URL url){this.url = url;}

    @Override
    protected AsyncResult<String> doInBackground(String... params){
        AsyncResult<String> result;

        HttpMethod httpMethod = HttpMethod.valueOf(params[0]);
        String route = params[1];
        String whatToRead = params[2];
        String payload = (params.length >= 4) ? params[3] : null;

        try {
            if (payload != null && httpMethod == HttpMethod.GET){
                route += "?" + payload;
            }

            url = new URL(url+ "/" + route);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            if (token != null){ // token is null if nobody is logged in
                connection.setRequestProperty("Token", token);
            }

            if (payload != null && httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT || httpMethod == HttpMethod.DELETE){
                write(connection, httpMethod, payload);
            }

            result = new AsyncResult<>(read(connection, ResultType.valueOf(whatToRead)));

            connection.disconnect();
        }
        catch(Exception ex){
            result = new AsyncResult<>(ex);
        }
        return result;
    }

    private void write (HttpURLConnection connection, HttpMethod httpMethod, String json) throws Exception {
        connection.setRequestMethod(httpMethod.toString());
        connection.setRequestProperty("Content-Type", "appliocation/json");

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        writer.write(json);
        writer.flush();
        writer.close();

        connection.getResponseCode();
    }


    private String read(HttpURLConnection connection, ResultType resType) throws Exception{
        String result = null;
        switch (resType){
            case CONTENT: //to get data from the database
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null){
                    sb.append(line);
                }

                result = sb.toString();
                reader.close();
                break;

            case TOKEN: // if a Login or register was done
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
