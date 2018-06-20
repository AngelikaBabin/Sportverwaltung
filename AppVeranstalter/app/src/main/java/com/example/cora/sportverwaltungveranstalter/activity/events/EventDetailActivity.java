package com.example.cora.sportverwaltungveranstalter.activity.events;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.method.KeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.ExposingActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncWebserviceTask;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Sportart;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod.GET;
import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod.PUT;

public class EventDetailActivity extends ExposingActivity implements AdapterView.OnItemSelectedListener, AsyncTaskHandler {
    EditText editText_title, editText_sports, editText_date, editText_location, editText_maxParticipators, editText_details;
    FloatingActionButton fabuttonEdit;
    FloatingActionButton fabuttonSave;
    Spinner spinner_sports;
    Veranstaltung currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        currentEvent = new Gson().fromJson(getIntent().getExtras().getString("event"), Veranstaltung.class);

        initComponents();
        setComponents();
        registerEventhandlers();
        setNotEditable();
    }

    private void initComponents() {
        editText_title = findViewById(R.id.editText_title);
        editText_sports = findViewById(R.id.editText_sports);
        editText_date = findViewById(R.id.editText_date);
        editText_location = findViewById(R.id.editText_location);
        editText_maxParticipators = findViewById(R.id.editText_maxParticipators);
        editText_details = findViewById(R.id.editText_details);
        spinner_sports = findViewById(R.id.spinner_sports);
        fabuttonSave = findViewById(R.id.fabSave);
        fabuttonEdit = findViewById(R.id.fabEdit);
    }

    private void setComponents(){
        editText_title.setText(currentEvent.getName());
        editText_sports.setText(currentEvent.getSportart());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        editText_date.setText(dateFormatter.format(currentEvent.getDatetime()));
        editText_location.setText(currentEvent.getLocation());
        editText_maxParticipators.setText(String.valueOf(currentEvent.getMaxTeilnehmer()));
        editText_details.setText(currentEvent.getDetails());
    }

    private void setNotEditable(){
        editText_title.setTag(editText_title.getKeyListener());
        editText_title.setKeyListener(null);

        if (spinner_sports.getSelectedItem() != null) {
            editText_sports.setText(spinner_sports.getSelectedItem().toString());
        }
        ViewGroup.LayoutParams lp = editText_sports.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        spinner_sports.setVisibility(View.INVISIBLE);
        editText_sports.setLayoutParams(lp);
        editText_sports.setTag(editText_sports.getKeyListener());
        editText_sports.setKeyListener(null);

        editText_date.setTag(editText_date.getKeyListener());
        editText_date.setKeyListener(null);

        editText_location.setTag(editText_location.getKeyListener());
        editText_location.setKeyListener(null);

        editText_maxParticipators.setTag(editText_maxParticipators.getKeyListener());
        editText_maxParticipators.setKeyListener(null);

        editText_details.setTag(editText_details.getKeyListener());
        editText_details.setKeyListener(null);

        fabuttonSave.setVisibility(View.INVISIBLE);
        fabuttonEdit.setVisibility(View.VISIBLE);
    }

    private void setEditable(){
        String sp;
        editText_details.setKeyListener((KeyListener)editText_details.getTag());
        editText_date.setKeyListener((KeyListener)editText_date.getTag());
        editText_location.setKeyListener((KeyListener)editText_location.getTag());
        editText_maxParticipators.setKeyListener((KeyListener)editText_maxParticipators.getTag());
        editText_details.setKeyListener((KeyListener)editText_details.getTag());

        sp = editText_sports.getText().toString();
        ViewGroup.LayoutParams lp = editText_sports.getLayoutParams();
        lp.width = 260;
        editText_sports.setLayoutParams(lp);
        editText_sports.setText(R.string.text_viewText_sportart);
        spinner_sports.setVisibility(View.VISIBLE);
        fillSpinner(sp);

        fabuttonSave.setVisibility(View.VISIBLE);
        fabuttonEdit.setVisibility(View.INVISIBLE);
    }


    private void registerEventhandlers() {
        fabuttonEdit.setOnClickListener(view -> setEditable());

        fabuttonSave.setOnClickListener(view -> {
            try {
                setNotEditable();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
                currentEvent.setDatetime(dateFormatter.parse(editText_date.getText().toString()));
                currentEvent.setDetails(editText_details.getText().toString());
                currentEvent.setLocation(editText_location.getText().toString());
                currentEvent.setName(editText_title.getText().toString());
                currentEvent.setMaxTeilnehmer(Integer.parseInt(editText_maxParticipators.getText().toString()));

                JsonObject json = new JsonObject();
                json.addProperty("name", currentEvent.getName());
                json.addProperty("details", currentEvent.getDetails());
                json.addProperty("location", currentEvent.getLocation());
                json.addProperty("maxTeilnehmer", currentEvent.getMaxTeilnehmer());
                json.addProperty("datetime", dateFormatter.format(currentEvent.getDatetime()));
                json.addProperty("sportart", spinner_sports.getSelectedItem().toString());

                AsyncWebserviceTask task = new AsyncWebserviceTask(PUT, "events", this, getApplicationContext());
                task.execute(null, json.toString());
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

    private void fillSpinner(String sp){
        this.spinner_sports.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Sportart.values()));
        this.spinner_sports.setSelection(getIndex(sp));
        this.spinner_sports.setOnItemSelectedListener(this);
    }

    private int getIndex(String sp){
        int position = -1;
        int i=0;
        while (position == -1 && i < spinner_sports.getAdapter().getCount()){
            if (spinner_sports.getItemAtPosition(i).toString().equals(sp)){
                position = i;
            }
            i++;
        }
        return position;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(EventDetailActivity.this);
        progDialog.setMessage("Logging in...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(true);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        try{
            progDialog.dismiss();
            Toast.makeText(this, "Data changed!", Toast.LENGTH_LONG).show();
        } catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Error err) {
        try{
            progDialog.cancel();
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
        } catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
