package com.example.cora.sportverwaltung.businesslogic.connection;

/**
 * @kandut everything
 */

public class AsyncTaskResult {
    private int statusCode;
    private String content;
    private Error error;

    public Error getError() {
        return error;
    }

    public AsyncTaskResult(int statusCode, String content) {
        super();
        this.statusCode = statusCode;
        this.content = content;
    }

    public AsyncTaskResult(Exception ex) {
        super();
        this.error = new Error(ex);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }
}
