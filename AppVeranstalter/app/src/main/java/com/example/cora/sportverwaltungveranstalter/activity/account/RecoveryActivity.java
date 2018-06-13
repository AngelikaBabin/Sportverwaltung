package com.example.cora.sportverwaltungveranstalter.activity.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.ExposingActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncTaskHandler;

public class RecoveryActivity extends ExposingActivity implements AsyncTaskHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onSuccess(int statusCode, String content) {

    }

    @Override
    public void onError(Error err) {

    }
}
