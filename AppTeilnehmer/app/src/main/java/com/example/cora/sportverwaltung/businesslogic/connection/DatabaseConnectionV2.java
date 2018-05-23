package com.example.cora.sportverwaltung.businesslogic.connection;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

/**
 * Created by nicok on 21.05.2018 ^-^.
 */
public class DatabaseConnectionV2 {
    public static void login(String email, String password, AsyncHttpResponseHandler handler) throws UnsupportedEncodingException {
        RequestParams params = new RequestParams("email", email, "password", password);

        HttpClient.post("/login", params, handler);
    }
}
