package com.example.cora.sportverwaltung.activity.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.ExposingActivity;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncWebserviceTask;
import com.google.gson.JsonObject;

import static com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod.POST;

/**
 * @kandut everything (which is not a lot)
 */

public class RecoveryActivity extends ExposingActivity implements AsyncTaskHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        initEventhandler();
    }

    private void initEventhandler() {
        findViewById(R.id.button_recover).setOnClickListener(view -> {
            try {
                // get email string
                TextView editText_email = findViewById(R.id.editText_email);
                String email = editText_email.getText().toString();

                // build json
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("email", email);

                // request recovery email
                AsyncWebserviceTask task = new AsyncWebserviceTask(POST, "recover", this, getApplicationContext());
                task.execute(null, jsonObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(this);
        progDialog.setMessage("Checking email...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
        switch (statusCode) {
            case 200:
                Toast.makeText(this, "Recovery email sent", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RecoveryActivity.this, LoginActivity.class));
                finish();
                break;

            case 403:
                Toast.makeText(this, "Acount doesn't exist", Toast.LENGTH_SHORT).show();

            default:
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        Toast.makeText(this, "Unknown email", Toast.LENGTH_LONG).show();
    }
}
