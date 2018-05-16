package com.example.cora.sportverwaltungveranstalter.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.ConnectionToDatabase;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Credentials;

public class LoginActivity extends ConnectionToDatabase {
    Button button_login, button_register, button_forgotPassword;
    EditText editText_email, editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        registerEventhandlers();
    }

    private void initComponents()
    {
        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_password);
        button_forgotPassword = (Button) findViewById(R.id.button_forgotPassword);
    }

    private void registerEventhandlers(){
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Credentials credentials = new Credentials(editText_email.getText().toString(), editText_password.getText().toString());
                    String token = connection.login(credentials);

                    if (token != null){
                        //startActivity(new Intent(LoginActivity.this, EventsSwipeActivity.class));
                        Toast.makeText(LoginActivity.this, "Login erfolgreich", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Wrong Username or password.", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception ex) {
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        button_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Sending recovery email", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
