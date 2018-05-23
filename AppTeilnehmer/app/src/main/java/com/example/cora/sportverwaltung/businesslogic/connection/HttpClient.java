package com.example.cora.sportverwaltung.businesslogic.connection;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

/**
 * Created by nicok on 21.05.2018 ^-^.
 */
public class HttpClient {
    private static final String BASE_URL = "http://10.0.0.28:8080/SportVerwaltung_WebServices/webresources";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException {
        params.setUseJsonStreamer(true);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
