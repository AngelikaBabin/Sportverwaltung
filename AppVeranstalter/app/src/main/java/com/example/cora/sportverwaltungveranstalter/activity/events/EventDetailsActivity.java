package com.example.cora.sportverwaltungveranstalter.activity.events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;

public class EventDetailsActivity extends AppCompatActivity {
    Veranstaltung selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        selectedEvent = new Gson().fromJson(this.getIntent().getExtras().getString("event"), Veranstaltung.class);

    }
}
