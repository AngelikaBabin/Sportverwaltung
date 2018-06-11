package com.example.cora.sportverwaltungveranstalter.businesslogic.connection;

/**
 * @babin
 */

public interface AsyncTaskHandler {
    void onPreExecute();
    void onSuccess(int statusCode, String content);
    void onError(Error err);
}
