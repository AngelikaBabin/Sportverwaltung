package com.example.cora.sportverwaltung.businesslogic.connection;

/**
 * Created by nicok on 28.05.2018 ^-^.
 */
public interface AsyncTaskHandler {
    void onPreExecute();
    void onSuccess(int statusCode, String content);
    void onError(Error err);
}
