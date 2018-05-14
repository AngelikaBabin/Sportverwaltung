package com.example.cora.sportverwaltung.activity.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cora.sportverwaltung.businesslogic.connection.DatabaseConnection;

public class ConnectionActivity extends AppCompatActivity {

    // provide connection to webservice for activities
    protected DatabaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init connection
        connection = DatabaseConnection.getInstance();
    }
}
