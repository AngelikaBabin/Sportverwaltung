package com.example.cora.sportverwaltung.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cora.sportverwaltung.R;

public class InfoAllEventsActivity extends AppCompatActivity {

    View view;
    TextView textView_header, textView_Details;
    TextView textView_date, textView_place, textView_participator, textView_organizer;
    Button button_participate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_all_events);

        getViewElements();
        registerEventhandlers();
    }

    private void getViewElements() {
        textView_header = view.findViewById(R.id.textView_header);
        textView_Details = view.findViewById(R.id.textView_Details);
        textView_date = view.findViewById(R.id.textView_date);
        textView_place = view.findViewById(R.id.textView_place);
        textView_participator = view.findViewById(R.id.textView_participator);
        textView_organizer = view.findViewById(R.id.textView_organizer);
        button_participate = view.findViewById(R.id.button_participate);
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
