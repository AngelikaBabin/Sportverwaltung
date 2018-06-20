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
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Veranstalter;

import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod.GET;
import static java.lang.Enum.valueOf;

/**
 * @babin
 */
public class ProfileActivity extends BaseActivity implements AsyncTaskHandler {

    // UI references
    private TextView textView_name, textView_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_profile);

        initUIReferences();
        displayData();

        Toast.makeText(this, preferences.getString("ip", ""), Toast.LENGTH_SHORT).show();

    }

    private void initUIReferences() {
        // view references
        textView_name = findViewById(R.id.textView_name);
        textView_email = findViewById(R.id.textView_email);
    }

    private void displayData() {
        try {
            // get user from webservice
            AsyncWebserviceTask task = new AsyncWebserviceTask(GET, "veranstalter", this, getApplicationContext());
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

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
<<<<<<< HEAD
        Veranstalter t = gson.fromJson(content, Veranstalter.class);
        textView_name.setText(t.getName());
        textView_email.setText(t.getEmail());
=======
        if (statusCode == 200) {
            if (content != null && content != "") {
                Veranstalter v = gson.fromJson(content, Veranstalter.class);
                textView_name.setText(v.getName());
                textView_email.setText(v.getEmail());
            } else {
                super.onSuccess(statusCode, content);
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
>>>>>>> 7ff8864f16ef6f4da62b29cbcb3e09ffadf5c3f7
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
