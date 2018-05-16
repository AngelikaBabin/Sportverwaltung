package com.example.cora.sportverwaltung.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.ConnectionActivity;

public class RecoveryActivity extends ConnectionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        findViewById(R.id.button_recover).setOnClickListener(view -> {
            try {
                TextView editText_email = findViewById(R.id.editText_email);
                String email = editText_email.getText().toString();

                int status = connection.sendRecoveryEmail(email);

                if(status == 200) {
                    startActivity(new Intent(RecoveryActivity.this, VerifyActivity.class));
                } else{
                    throw new Exception("ERROR: " + status);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
