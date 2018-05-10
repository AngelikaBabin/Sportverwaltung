package com.example.cora.sportverwaltung.activity.account;

import android.os.Bundle;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.BaseActivity;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // display layout
        setContent(R.layout.activity_profile);
    }
}
