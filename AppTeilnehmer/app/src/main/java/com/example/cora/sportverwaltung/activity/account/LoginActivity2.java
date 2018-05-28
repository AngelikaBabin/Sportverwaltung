package com.example.cora.sportverwaltung.activity.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.ConnectionActivity;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncWebserviceTask;
import com.example.cora.sportverwaltung.businesslogic.data.Credentials;
import com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod;

import static com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod.POST;

public class LoginActivity2 extends ConnectionActivity implements AsyncTaskHandler {
    Button button_login, button_register, button_forgotPassword;
    EditText editText_email, editText_password;
    private ProgressDialog progDialog;

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
        button_forgotPassword = findViewById(R.id.button_forgotPassword);
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

                    Credentials c = new Credentials(email, password);

                    AsyncWebserviceTask task = new AsyncWebserviceTask(POST, "login", LoginActivity2.this);
                    task.execute();

                } catch (Exception ex) {
                    Toast.makeText(LoginActivity2.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity2.this, RegisterActivity.class));
            }
        });

        button_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity2.this, RecoveryActivity.class));
            }
        });
    }

    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(LoginActivity2.this);
        progDialog.setMessage("Loading...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(true);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
        startActivity(new Intent(LoginActivity2.this, ProfileActivity.class));
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        Toast.makeText(LoginActivity2.this, "Wrong username or password", Toast.LENGTH_LONG).show();
    }
}
