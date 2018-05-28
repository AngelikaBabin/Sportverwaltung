package com.example.nicok.testapplication;

/**
 * Created by nicok on 25.05.2018 ^-^.
 */
public interface AsyncTaskHandler {
    void onPreExecute();
    void onSuccess(String result);
    void onError(Error err);
}
