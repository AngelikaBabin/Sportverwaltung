package com.example.cora.sportverwaltung.businesslogic.connection;

/**
 * @kandut everything
 */

public interface AsyncTaskHandler {
    void onPreExecute();
    void onSuccess(int statusCode, String content);
    void onError(Error err);
}
