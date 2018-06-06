package com.example.cora.sportverwaltung.activity.base;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

/**
 * @kandut generalization from former activities
 */

public class ExposingActivity extends AppCompatActivity {

    // expose progressDialog
    protected ProgressDialog progDialog;

    // expose global gson
    protected Gson gson;

    // expose shared preferences
    protected SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initMisc();
    }

    private void initMisc(){
        gson = new Gson();
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
}
