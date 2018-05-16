package com.example.cora.sportverwaltung.activity.events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.businesslogic.data.Teilnehmer;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;

import java.util.ArrayList;

public class InfoPastEventsActivity extends AppCompatActivity {
    TextView textView_header, textView_date, textView_place, textView_organizer, textView_rank, textView_points;
    ListView listView_topDrei;
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
        setValuesInFields();
    }

    private void setValuesInFields() {
        textView_header.setText(selectedEvent.getName());
        textView_date.setText(selectedEvent.getDatetime().toString());
        textView_place.setText(selectedEvent.getLocation().toString());
        textView_organizer.setText(selectedEvent.getVeranstalter().toString());
        //ToDo

        //setTopDreiListView();

        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.test_array_Teilnehmer, android.R.layout.simple_list_item_1);
        listView_topDrei.setAdapter(aa);
    }

    /*private void setTopDreiListView() {
        ArrayList<String> result =
        setAdapterData(result);
    }


    private void setAdapterData(ArrayList<String> result) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
        listView_topDrei.setAdapter(adapter);
    }*/

    private void getViewElements() {
        textView_header = findViewById(R.id.textView_header);
        textView_date = findViewById(R.id.textView_date);
        textView_place = findViewById(R.id.textView_place);
        textView_organizer = findViewById(R.id.textView_organizer);
        textView_rank = findViewById(R.id.textView_rank);
        textView_points = findViewById(R.id.textView_points);
        listView_topDrei = findViewById(R.id.listView_topDrei);
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
