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
<<<<<<< HEAD
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncWebserviceTask;
=======
>>>>>>> 7ff8864f16ef6f4da62b29cbcb3e09ffadf5c3f7
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Sportart;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod.GET;

<<<<<<< HEAD

public class MyEventsActivity extends BaseActivity implements AsyncTaskHandler {
=======
public class MyEventsActivity extends BaseActivity implements AsyncTaskHandler{
>>>>>>> 7ff8864f16ef6f4da62b29cbcb3e09ffadf5c3f7
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
        progDialog.setMessage("Getting events...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();

        Type collectionType = new TypeToken<ArrayList<Veranstaltung>>() {
        }.getType();

        events = new Gson().fromJson(content, collectionType);
        setAdapterData(events);
        /*
        button_participate.setEnabled(false);
        Toast.makeText(InfoAllEventsActivity.this, "You are now participating", Toast.LENGTH_LONG).show();
        */
    }

    @Override
<<<<<<< HEAD
=======
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
            case 201:
                Toast.makeText(MyEventsActivity.this, "Show all Events", Toast.LENGTH_LONG).show();
                break;

            case 403:
                Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
>>>>>>> 7ff8864f16ef6f4da62b29cbcb3e09ffadf5c3f7
    public void onError(Error err) {
        progDialog.cancel();
        Toast.makeText(this, err.getMessage(), Toast.LENGTH_SHORT).show();
    }
<<<<<<< HEAD
=======


>>>>>>> 7ff8864f16ef6f4da62b29cbcb3e09ffadf5c3f7
}
