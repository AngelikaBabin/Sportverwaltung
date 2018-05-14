package com.example.cora.sportverwaltung.activity.events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;

public class InfoPastEventsActivity extends AppCompatActivity {
    TextView textView_header, textView_date, textView_place, textView_participator, textView_organizer, textView_rank, textView_points;
    ListView listView_pastEvents;
    Button button_close;
    private Veranstaltung selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_past_events);
        //get selected Event data from EventsFragment intent
        selectedEvent = new Gson().fromJson(this.getIntent().getExtras().getString("event"), Veranstaltung.class);
        getViewElements();
        registerEventhandlers();
    }

    private void getViewElements() {
        textView_header = findViewById(R.id.textView_header);
        textView_date = findViewById(R.id.textView_date);
        textView_place = findViewById(R.id.textView_place);
        textView_participator = findViewById(R.id.textView_participator);
        textView_organizer = findViewById(R.id.textView_organizer);
        textView_rank = findViewById(R.id.textView_rank);
        textView_points = findViewById(R.id.textView_points);
        listView_pastEvents = findViewById(R.id.listView_pastEvents);
        button_close = findViewById(R.id.button_logout);
    }

    private void registerEventhandlers() {
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDO
            }
        });
    }
}
