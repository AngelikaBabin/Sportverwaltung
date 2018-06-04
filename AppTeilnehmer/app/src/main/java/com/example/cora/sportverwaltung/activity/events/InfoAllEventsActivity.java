package com.example.cora.sportverwaltung.activity.events;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.ConnectionActivity;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;

public class InfoAllEventsActivity extends ConnectionActivity {

    TextView textView_header, textView_Details;
    TextView textView_date, textView_place, textView_participator, textView_organizer, textView_sport;
    Button button_participate;

    private Veranstaltung selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_all_events);

        //get Json form intent
        String strEvent = this.getIntent().getExtras().getString("event");
        selectedEvent = new Gson().fromJson(strEvent, Veranstaltung.class);

        getViewElements();
        registerEventhandlers();
        setValuesInFields();
    }

    private void setValuesInFields() {
        textView_header.setText(selectedEvent.getName());
        textView_date.setText("Thu 22. May");
        textView_place.setText(selectedEvent.getLocation().toString());
        textView_participator.setText(String.valueOf(selectedEvent.getMaxTeilnehmer()));
        textView_organizer.setText(selectedEvent.getVeranstalter().toString());
        textView_Details.setText(selectedEvent.getDetails());
        textView_sport.setText(selectedEvent.getSportart());
    }

    private void getViewElements() {
        textView_header = findViewById(R.id.textView_header);
        textView_Details = findViewById(R.id.textView_Details);
        textView_date = findViewById(R.id.textView_date);
        textView_place = findViewById(R.id.textView_place);
        textView_participator = findViewById(R.id.textView_participator);
        textView_organizer = findViewById(R.id.textView_organizer);
        button_participate = findViewById(R.id.button_participate);
        textView_sport = findViewById(R.id.textView_sport);
    }

    private void registerEventhandlers() {
        button_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                connection.participate(selectedEvent.getId());
                Toast.makeText(InfoAllEventsActivity.this, "You are now participating", Toast.LENGTH_LONG).show();
                button_participate.setEnabled(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(InfoAllEventsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
