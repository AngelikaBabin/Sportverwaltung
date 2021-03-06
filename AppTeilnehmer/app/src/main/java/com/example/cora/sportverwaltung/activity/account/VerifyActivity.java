package com.example.cora.sportverwaltung.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cora.sportverwaltung.R;

/**
 * @kandut everything (which is not a lot)
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
            finish();
        });
    }
}
