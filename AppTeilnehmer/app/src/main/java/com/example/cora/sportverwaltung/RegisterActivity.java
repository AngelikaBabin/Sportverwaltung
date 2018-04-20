package com.example.cora.sportverwaltung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cora.sportverwaltung.businesslogic.DatabaseConnection;
import com.example.cora.sportverwaltung.businesslogic.data.Teilnehmer;

public class RegisterActivity extends AppCompatActivity {

    Button button_register;
    EditText editText_email, editText_name, editText_password, editText_passwordConfirm;

    DatabaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // create database connection
        connection = new DatabaseConnection();

        // get view elements
        getViewElements();

        // register eventhandlers
        registerEventhandlers();
    }

    private void getViewElements(){
        button_register = findViewById(R.id.btnRegistrieren);
        editText_email = findViewById(R.id.txtEmail);
        editText_name = findViewById(R.id.txtBenutzer);
        editText_password = findViewById(R.id.txtPassword);
        editText_passwordConfirm = findViewById(R.id.txtPassBes);
    }

    private void registerEventhandlers() {
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // get view contents
                    String email = editText_email.getText().toString();
                    String name = editText_name.getText().toString();
                    String password = editText_password.getText().toString();
                    String passwordConfirm = editText_passwordConfirm.getText().toString();

                    // check password
                    if (password.equals(passwordConfirm)) {
                        // create Teilnehmer
                        Teilnehmer t = new Teilnehmer(email, name, password);

                        // register in database
                        String token = connection.registerTeilnehmer(t);

                        // open menu activity
                        startActivity(new Intent(RegisterActivity.this, MenuActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT);
                    }
                } catch (Exception ex) {
                    Toast.makeText(RegisterActivity.this, ex.getMessage(), Toast.LENGTH_SHORT);
                    ex.printStackTrace();
                }
            }
        });
    }
}
