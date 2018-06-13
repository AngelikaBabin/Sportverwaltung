package com.example.cora.sportverwaltungveranstalter.activity.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.ExposingActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncWebserviceTask;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Credentials;

import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod.POST;

/**
 * @babin
 */
public class LoginActivity extends ExposingActivity implements AsyncTaskHandler {
    Button button_login, button_register, button_forgotPassword;
    EditText editText_email, editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUIComponents();
        registerEventhandlers();
    }

    private void initUIComponents()
    {
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_password);
        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);
        button_forgotPassword = (Button) findViewById(R.id.button_forgotPassword);
    }

    private void registerEventhandlers(){
        button_login.setOnClickListener(view -> {
            try {
                String email = editText_email.getText().toString();
                String password = editText_password.getText().toString();

                Credentials c = new Credentials(email, password);
                String json = gson.toJson(c);

                AsyncWebserviceTask task = new AsyncWebserviceTask(POST, "login", LoginActivity.this, getApplicationContext());
                task.execute(null, json);

            } catch (Exception ex) {
                Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        });

        button_register.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        button_forgotPassword.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RecoveryActivity.class)));
    }

    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(LoginActivity.this);
        progDialog.setMessage("Logging in...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
        preferences.edit().putString("EMAIL", editText_email.getText().toString()).apply();
        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
    }
}
