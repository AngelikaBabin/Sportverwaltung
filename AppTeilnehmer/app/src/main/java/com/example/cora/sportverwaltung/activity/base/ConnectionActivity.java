package com.example.cora.sportverwaltung.activity.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

public class ConnectionActivity extends AppCompatActivity {

    // loading dialog
    protected ProgressDialog progDialog;

    // provide connection to webservice for activities
    protected Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //gson
        gson = new Gson();
    }
}
