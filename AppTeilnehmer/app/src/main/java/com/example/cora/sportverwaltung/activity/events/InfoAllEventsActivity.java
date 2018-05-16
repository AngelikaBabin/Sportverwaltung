package com.example.cora.sportverwaltung.activity.events;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;

public class InfoAllEventsActivity extends AppCompatActivity {

    TextView textView_header, textView_Details;
    TextView textView_date, textView_place, textView_participator, textView_organizer;
    Button button_participate;

    private Veranstaltung selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_all_events);
        //get Json form intent
        String strEvent = this.getIntent().getExtras().getString("event");
        getViewElements();
        registerEventhandlers();
        setValuesInFields();
    }

    private void setValuesInFields() {
        textView_header.setText(selectedEvent.getName());
        textView_date.setText(selectedEvent.getDatetime().toString());
        textView_place.setText(selectedEvent.getLocation().toString());
        textView_participator.setText(selectedEvent.getMaxTeilnehmer());
        textView_organizer.setText(selectedEvent.getVeranstalter().toString());
    }

    private void getViewElements() {
        textView_header = findViewById(R.id.textView_header);
        textView_Details = findViewById(R.id.textView_Details);
        textView_date = findViewById(R.id.textView_date);
        textView_place = findViewById(R.id.textView_place);
        textView_participator = findViewById(R.id.textView_participator);
        textView_organizer = findViewById(R.id.textView_organizer);
        button_participate = findViewById(R.id.button_participate);
    }

    private void registerEventhandlers() {
        button_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDO
            }
        });
    }
}
