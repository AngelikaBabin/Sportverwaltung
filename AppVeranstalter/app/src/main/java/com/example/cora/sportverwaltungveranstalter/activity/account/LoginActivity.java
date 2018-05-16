package com.example.cora.sportverwaltungveranstalter.activity.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cora.sportverwaltungveranstalter.MenuActivity;
import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.RegisterActivity;
import com.example.cora.sportverwaltungveranstalter.activity.base.ConnectionToDatabase;

public class LoginActivity extends ConnectionToDatabase {
    Button login, registrieren;
    EditText txtBenutzer, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            }
        });

        registrieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void initComponents()
    {
        login = (Button) findViewById(R.id.btnLogin);
        registrieren = (Button) findViewById(R.id.btnRegistrieren);
        txtBenutzer = (EditText) findViewById(R.id.txtBenutzer);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
    }
}
