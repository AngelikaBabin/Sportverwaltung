package com.example.cora.sportverwaltung.activity.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cora.sportverwaltung.businesslogic.connection.DatabaseConnection;
import com.google.gson.Gson;

public class ConnectionActivity extends AppCompatActivity {

    // loading dialog
    protected ProgressDialog progDialog;

    // provide connection to webservice for activities
    protected DatabaseConnection connection;
    protected Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init connection
        connection = DatabaseConnection.getInstance();

        //gson
        gson = new Gson();
    }
}
