package com.example.cora.sportverwaltungveranstalter.activity.events;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.cora.sportverwaltungveranstalter.R;

public class EventDetailActivity extends AppCompatActivity {

    FloatingActionButton fabuttonEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        initComponents();
        registerEventhandlers();
    }

    private void initComponents() {
        fabuttonEdit = findViewById(R.id.fab);
    }

    private void registerEventhandlers() {
        fabuttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
