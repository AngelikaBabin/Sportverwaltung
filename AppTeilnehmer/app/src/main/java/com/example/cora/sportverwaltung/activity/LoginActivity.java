package com.example.cora.sportverwaltung.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.businesslogic.DatabaseConnection;
import com.example.cora.sportverwaltung.businesslogic.data.Credentials;

public class LoginActivity extends AppCompatActivity {
    Button button_login, button_register;
    EditText editText_email, editText_password;
    DatabaseConnection connection = DatabaseConnection.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getViewElements();

        registerEventhandlers();
    }

    private void getViewElements() {
        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);
        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);
    }

    private void registerEventhandlers() {
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String email = editText_email.getText().toString();
                    String password = editText_password.getText().toString();

                    Credentials credentials = new Credentials(email, password);

                    connection.participate(12, 17);

                    String token = connection.login(credentials);

                    if (token != null) {
                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
