package com.example.cora.sportverwaltungveranstalter.activity.events;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.BaseActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncWebserviceTask;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Sportart;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod.GET;

public class MyEventsActivity extends BaseActivity implements AsyncTaskHandler {
    FloatingActionButton faButton_addEvent;
    ListView listView_events;
    private ArrayList<Veranstaltung> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContent(R.layout.activity_my_events);

            initComponents();
            registerEventhandlers();

            AsyncWebserviceTask task = new AsyncWebserviceTask(GET, "events", this, this.getApplicationContext());
            task.execute("filter=VERANSTALTER");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
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

            Intent intent = new Intent(MyEventsActivity.this, EventDetailActivity.class);
            intent.putExtra("event", json);
            startActivity(intent);
        });
    }

    private void setAdapterData(ArrayList<Veranstaltung> entries) {
        EventListAdapter adapter = new EventListAdapter(MyEventsActivity.this, entries);
        listView_events.setAdapter(adapter);
    }


    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(this);
        progDialog.setMessage("Loading Events...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
        switch (statusCode) {
            case 200:
                Type collectionType = new TypeToken<ArrayList<Veranstaltung>>() {
                }.getType();

                events = new Gson().fromJson(content, collectionType);
                setAdapterData(events);
                break;

            case 403:
                Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        Toast.makeText(this, err.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
