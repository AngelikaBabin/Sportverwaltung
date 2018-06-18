package com.example.cora.sportverwaltung.activity.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.ExposingActivity;
import com.example.cora.sportverwaltung.activity.settings.SettingsActivity;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncWebserviceTask;
import com.example.cora.sportverwaltung.businesslogic.data.Credentials;

import static com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod.POST;

/**
 * @kandut async listener and code cleanup
 * @kumnig gui structure and activity code
 * @rajic gui design
 */

public class LoginActivity extends ExposingActivity implements AsyncTaskHandler {

    // UI references
    private Button button_login, button_register, button_forgotPassword, button_settings;
    private EditText editText_email, editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUIReferences();
        initEventhandlers();
    }

    private void initUIReferences() {
        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);
        button_settings = findViewById(R.id.button_settings);
        button_forgotPassword = findViewById(R.id.button_forgotPassword);
        editText_email = findViewById(R.id.editText_email);
        editText_email.setText(preferences.getString("EMAIL", ""));
        editText_password = findViewById(R.id.editText_password);
    }

    private void initEventhandlers() {
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

        button_settings.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SettingsActivity.class)));

    }

    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(LoginActivity.this);
        progDialog.setMessage("Logging in...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(true);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        try{
            progDialog.dismiss();
            preferences.edit().putString("EMAIL", editText_email.getText().toString()).apply();
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        } catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Error err) {
        try{
            progDialog.cancel();
            Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
        } catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
