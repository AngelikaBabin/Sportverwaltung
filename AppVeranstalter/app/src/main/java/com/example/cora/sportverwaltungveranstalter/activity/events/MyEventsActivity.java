package com.example.cora.sportverwaltungveranstalter.activity.events;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.BaseActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyEventsActivity extends BaseActivity {
    FloatingActionButton faButton_addEvent;
    ListView listView_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.content_my_events);

        initComponents();
        registerEventhandlers();
        getDataFromDB(); //Kraschl

    }

    public void initComponents(){
        faButton_addEvent = (FloatingActionButton) findViewById(R.id.fabutton_AddEvent);
        listView_events = (ListView) findViewById(R.id.listView_events);
    }


    private void registerEventhandlers() {
        faButton_addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyEventsActivity.this, AddEventActivity.class));
            }
        });

        listView_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi (api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Veranstaltung event = (Veranstaltung) listView_events.getItemAtPosition(i);
                Gson gson = new Gson();
                String json = gson.toJson(event, Veranstaltung.class);
                Intent intent = new Intent(MyEventsActivity.this, EventDetailsActivity.class);
                intent.putExtra("event", json);
                startActivity(intent);
            }
        });
    }

    private void setAdapterData(ArrayList<Veranstaltung> entries) {
        EventListAdapter adapter = new EventListAdapter(MyEventsActivity.this, entries);
        listView_events.setAdapter(adapter);
    }


    private void getDataFromDB() {
        //mit der Mehtode setAdapterData die Daten in die Listview spielen
    }

}
