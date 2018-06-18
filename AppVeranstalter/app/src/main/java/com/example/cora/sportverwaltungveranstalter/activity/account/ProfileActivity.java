package com.example.cora.sportverwaltungveranstalter.activity.account;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.BaseActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncWebserviceTask;

import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod.GET;

/**
 * @babin
 */
public class ProfileActivity extends BaseActivity implements AsyncTaskHandler {

    // UI references
    private TextView textView_name, textView_email, textView_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_profile);

        initUIReferences();
        //displayData();

        Toast.makeText(this, preferences.getString("ip", ""), Toast.LENGTH_SHORT).show();

    }

    private void initUIReferences() {
        // view references
        textView_name = findViewById(R.id.textView_name);
        textView_email = findViewById(R.id.textView_email);
        textView_score = findViewById(R.id.textView_score);
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
        progDialog.setMessage("Logging in...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
       /* Teilnehmer t = gson.fromJson(content, Teilnehmer.class);
        textView_name.setText(t.getName());
        textView_email.setText(t.getEmail());
        textView_score.setText(t.getScore()+ " points");*/
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
