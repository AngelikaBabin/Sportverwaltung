package com.example.cora.sportverwaltungveranstalter.activity.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cora.sportverwaltungveranstalter.R;

/**
 * @babin
 */
public class VerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        initEventhandlers();
    }

    private void initEventhandlers() {
        findViewById(R.id.button_login).setOnClickListener(view -> {
            startActivity(new Intent(VerifyActivity.this, LoginActivity.class));
        });
    }
}
