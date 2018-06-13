package com.example.cora.sportverwaltungveranstalter.activity.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.BaseActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncTaskHandler;

/**
 * @babin
 */
public class ProfileActivity extends BaseActivity implements AsyncTaskHandler {

    private TextView textView_name, textView_email, textView_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initComponents();

    }

    private void initComponents() {
        // view references
        textView_name = findViewById(R.id.textView_name);
        textView_email = findViewById(R.id.textView_email);
        textView_score = findViewById(R.id.textView_score);
    }
}
