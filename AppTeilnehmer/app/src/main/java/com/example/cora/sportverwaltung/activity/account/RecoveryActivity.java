package com.example.cora.sportverwaltung.activity.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.ConnectionActivity;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncWebserviceTask;
import com.google.gson.JsonObject;

import java.nio.file.attribute.PosixFileAttributes;

import static com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod.POST;

public class RecoveryActivity extends ConnectionActivity implements AsyncTaskHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        findViewById(R.id.button_recover).setOnClickListener(view -> {
            try {
                TextView editText_email = findViewById(R.id.editText_email);
                String email = editText_email.getText().toString();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("email", email);

                AsyncWebserviceTask task = new AsyncWebserviceTask(POST, "recover", this);
                task.execute(jsonObject.toString());
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
        Toast.makeText(getApplicationContext(), "Recovery email sent", Toast.LENGTH_LONG).show();
        startActivity(new Intent(RecoveryActivity.this, LoginActivity.class));
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        Toast.makeText(this, "Unknown email", Toast.LENGTH_LONG).show();
    }
}
