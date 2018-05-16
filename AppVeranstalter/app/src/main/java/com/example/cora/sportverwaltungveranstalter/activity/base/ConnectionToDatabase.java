package com.example.cora.sportverwaltungveranstalter.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.DatabaseConnection;

public class ConnectionToDatabase extends AppCompatActivity {
    // provide connection to webservice for activities
    protected DatabaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init connection
        connection = DatabaseConnection.getInstance();
    }
}
