package com.example.cora.sportverwaltung.activity.events;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;

public class InfoMyEventsActivity extends AppCompatActivity {
    TextView textView_header, textView_date, textView_place, textView_organizer, textView_details;
    Button button_logout;

    private Veranstaltung selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_my_events);
        //get selected Event data from EventsFragment intent
        selectedEvent = new Gson().fromJson(this.getIntent().getExtras().getString("event"), Veranstaltung.class);
        getViewElements();
        registerEventhandlers();
        setValuesInFields();
    }

    private void setValuesInFields() {
        textView_header.setText(selectedEvent.getName());
        textView_date.setText("Thu 22. May");
        textView_place.setText(selectedEvent.getLocation().toString());
        textView_organizer.setText(selectedEvent.getVeranstalter().toString());
        textView_details.setText(selectedEvent.getDetails());
    }

    private void getViewElements() {
        textView_header = findViewById(R.id.textView_header);
        textView_details = findViewById(R.id.textView_details);
        textView_date = findViewById(R.id.textView_date);
        textView_place = findViewById(R.id.textView_place);
        textView_organizer = findViewById(R.id.textView_organizer);
        button_logout = findViewById(R.id.button_logout);
    }

    private void registerEventhandlers() {
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InfoMyEventsActivity.this, "You are no longer participating", Toast.LENGTH_LONG).show();
                button_logout.setEnabled(false);
            }
        });
    }
}
