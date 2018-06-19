package com.example.cora.sportverwaltung.activity.account;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.BaseActivity;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncWebserviceTask;
import com.example.cora.sportverwaltung.businesslogic.data.Teilnehmer;

import static com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod.GET;

/**
 * @kandut gui structure, async listener and code cleanup
 * @kumnig gui design
 */

public class ProfileActivity extends BaseActivity implements AsyncTaskHandler {

    // UI references
    private TextView textView_name, textView_email, textView_level, textView_score;
    private ProgressBar progressBar_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_profile);

        initUIReferences();
        displayData();
    }

    private void initUIReferences() {
        // view references
        textView_name = findViewById(R.id.textView_name);
        textView_email = findViewById(R.id.textView_email);
        textView_level = findViewById(R.id.textView_level);
        textView_score = findViewById(R.id.textView_score);
        progressBar_level = findViewById(R.id.progressBar_level);
    }

    private void displayData() {
        try {
            // get user from webservice
            AsyncWebserviceTask task = new AsyncWebserviceTask(GET, "teilnehmer", this, getApplicationContext());
            task.execute();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(ProfileActivity.this);
        progDialog.setMessage("Load data...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
        if (statusCode == 200) {
            if (content != null && content != "") {
                Teilnehmer t = gson.fromJson(content, Teilnehmer.class);
                textView_name.setText(t.getName());
                textView_email.setText(t.getEmail());
                textView_level.setText("Level " + getLevel(t.getScore()));
                textView_score.setText(t.getScore() + "pp");
                progressBar_level.setProgress(t.getScore() % 100);
            } else {
                super.onSuccess(statusCode, content);
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private int getLevel(int score) {
        int level = 1;
        double threshold = 10;
        while(score >= threshold){
            level++;
            score -= threshold;
            threshold *= (double)1 + (double)level/(double)100;
        }
        return level;
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
