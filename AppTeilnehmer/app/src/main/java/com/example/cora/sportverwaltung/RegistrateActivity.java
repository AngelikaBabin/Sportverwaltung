package com.example.cora.sportverwaltung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrateActivity extends AppCompatActivity {

    Button registrieren;
    EditText benutzer, email, password, passwordBest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        registrieren = (Button) findViewById(R.id.btnRegistrieren);
        benutzer = (EditText) findViewById(R.id.txtBenutzer);
        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPassword);
        passwordBest = (EditText) findViewById(R.id.txtPassBes);

        registrieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrateActivity.this, MenuActivity.class));
            }
        });
    }
}
