package com.example.nicok.testapplication;

/**
 * Created by nicok on 25.05.2018 ^-^.
 */
public class AsyncTaskResult<T> {
    private T result;
    private Error error;

    public T getResult() {
        return result;
    }

    public Error getError() {
        return error;
    }

    public AsyncTaskResult(T result) {
        super();
        this.result = result;
    }

    public AsyncTaskResult(Exception ex) {
        super();
        this.error = new Error(ex);
    }
}
