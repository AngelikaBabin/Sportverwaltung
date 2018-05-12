package com.example.cora.sportverwaltung.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
