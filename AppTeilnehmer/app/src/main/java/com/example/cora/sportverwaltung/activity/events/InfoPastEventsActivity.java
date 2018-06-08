package com.example.cora.sportverwaltung.activity.events;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class InfoPastEventsActivity extends AppCompatActivity {
    TextView textView_header, textView_date, textView_place, textView_organizer, textView_rank, textView_points, textView_sport;
    ListView listView_topDrei;
    private Veranstaltung selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_past_events);
        //get selected Event data from EventsFragment intent
        selectedEvent = new Gson().fromJson(this.getIntent().getExtras().getString("event"), Veranstaltung.class);
        getViewElements();
        setValuesInFields();
    }

    private void setValuesInFields() {
        textView_header.setText(selectedEvent.getName());
        textView_date.setText(new SimpleDateFormat("dd.MM.yyyy").format(selectedEvent.getDatetime())); //toDo isnt tested yet, test if it works and remove this toDo
        textView_place.setText(selectedEvent.getLocation().toString());
        textView_organizer.setText(selectedEvent.getVeranstalter().toString());
        textView_points.setText("40 pts");
        textView_rank.setText("1st");
        textView_sport.setText(selectedEvent.getSportart());
        setTopDreiListView();
    }

    private void setTopDreiListView() {
        ArrayList<String> result = new ArrayList<>();

        result.add("1. Nico");
        result.add("2. Cora");
        result.add("3. Angie");

        setAdapterData(result);
    }


    private void setAdapterData(ArrayList<String> result) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
        listView_topDrei.setAdapter(adapter);
    }

    private void getViewElements() {
        textView_header = findViewById(R.id.textView_header);
        textView_date = findViewById(R.id.textView_date);
        textView_place = findViewById(R.id.textView_place);
        textView_organizer = findViewById(R.id.textView_organizer);
        textView_rank = findViewById(R.id.textView_rank);
        textView_points = findViewById(R.id.textView_points);
        listView_topDrei = findViewById(R.id.listView_topDrei);
        textView_sport = findViewById(R.id.textView_sport);
    }
}
