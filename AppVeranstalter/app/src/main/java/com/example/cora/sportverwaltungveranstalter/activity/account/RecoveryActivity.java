package com.example.cora.sportverwaltungveranstalter.activity.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.ExposingActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncTaskHandler;

public class RecoveryActivity extends ExposingActivity implements AsyncTaskHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        initEventhandler();
    }

    private void initEventhandler() {
        findViewById(R.id.button_recover).setOnClickListener(view->{
           try {
               TextView editText_email = findViewById(R.id.editText_email);
               String email = editText_email.getText().toString();
           }
           catch (Exception ex){
               ex.printStackTrace();
               Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
           }
        });
    }

    @Override
    public void onPreExecute() {

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
