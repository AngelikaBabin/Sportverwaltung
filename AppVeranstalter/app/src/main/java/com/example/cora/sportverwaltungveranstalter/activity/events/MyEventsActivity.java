package com.example.cora.sportverwaltungveranstalter.activity.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.BaseActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Sportart;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MyEventsActivity extends BaseActivity implements AsyncTaskHandler{
    FloatingActionButton faButton_addEvent;
    ListView listView_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_my_events);

        initComponents();
        registerEventhandlers();
        getDataFromDB(); //TODO: Kraschl
    }

    public void initComponents(){
        faButton_addEvent = findViewById(R.id.fabutton_AddEvent);
        listView_events = findViewById(R.id.listView_events);
    }


    private void registerEventhandlers() {
        faButton_addEvent.setOnClickListener(view -> {
            startActivity(new Intent(MyEventsActivity.this, AddEventActivity.class));
        });

        listView_events.setOnItemClickListener((adapterView, view, i, l) -> {
            Veranstaltung event = (Veranstaltung) listView_events.getItemAtPosition(i);
            Gson gson = new Gson();
            String json = gson.toJson(event, Veranstaltung.class);
            Toast.makeText(MyEventsActivity.this, json.toString(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MyEventsActivity.this, EventDetailActivity.class);
            intent.putExtra("event", json);
            startActivity(intent);
        });
    }

    private void setAdapterData(ArrayList<Veranstaltung> entries) {
        EventListAdapter adapter = new EventListAdapter(MyEventsActivity.this, entries);
        listView_events.setAdapter(adapter);
    }


    private void getDataFromDB() {
        //TODO: mit der Mehtode setAdapterData die Daten in die Listview spielen
        //TODO: Testdaten löschen
        Veranstaltung vertest = new Veranstaltung(1, "test", "hoffe es geht", null, "London", Sportart.BASKETBALL.toString(), Calendar.getInstance().getTime(), 2, 1);
        ArrayList<Veranstaltung> test = new ArrayList<Veranstaltung>();
        test.add(vertest);
        setAdapterData(test);
    }

}
