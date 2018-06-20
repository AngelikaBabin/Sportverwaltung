package com.example.cora.sportverwaltungveranstalter.activity.events;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.ExposingActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncTaskHandler;
<<<<<<< HEAD
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncWebserviceTask;
=======
>>>>>>> 7ff8864f16ef6f4da62b29cbcb3e09ffadf5c3f7
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Sportart;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;

import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod.POST;
import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod.PUT;

/**
 * @babin GUI
 */
public class AddEventActivity extends ExposingActivity implements AdapterView.OnItemSelectedListener, AsyncTaskHandler{
    EditText editText_title, editText_date, editText_location, editText_participators, editText_details;
    Spinner spinner_sports;
    Button button_save;
    Button button_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toast.makeText(AddEventActivity.this, "in on Create", Toast.LENGTH_SHORT).show();
        initComponents();
        fillSpinner();
        registerEventhandlers();
    }

    private void initComponents() {
        editText_title = findViewById(R.id.editText_title);
        spinner_sports = findViewById(R.id.spinner_sportarten);
        editText_date = findViewById(R.id.editText_date);
        editText_location = findViewById(R.id.editText_location);
        editText_participators = findViewById(R.id.editText_maxParticipators);
        editText_details = findViewById(R.id.editText_details);
        button_cancel = findViewById(R.id.button_cancel);
        button_save = findViewById(R.id.button_save);
    }

    private void registerEventhandlers(){
        button_cancel.setOnClickListener(view -> startActivity(new Intent(AddEventActivity.this, MyEventsActivity.class)));

        button_save.setOnClickListener(view -> {
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
                JsonObject json = new JsonObject();
                json.addProperty("name", editText_title.getText().toString());
                json.addProperty("details", editText_details.getText().toString());
                json.addProperty("location", editText_location.getText().toString());
                json.addProperty("maxTeilnehmer", editText_participators.getText().toString());
                json.addProperty("datetime", editText_date.getText().toString());
                json.addProperty("sportart", spinner_sports.getSelectedItem().toString());

                AsyncWebserviceTask task = new AsyncWebserviceTask(POST, "events", this, getApplicationContext());
                task.execute(null, json.toString());
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

    private void fillSpinner(){
        this.spinner_sports.setAdapter(new ArrayAdapter<Sportart>(this, android.R.layout.simple_spinner_item, Sportart.values()));
        this.spinner_sports.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPreExecute() {
<<<<<<< HEAD
        progDialog = new ProgressDialog(AddEventActivity.this);
        progDialog.setMessage("Logging in...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(true);
=======
        progDialog = new ProgressDialog(this);
        progDialog.setMessage("Adding new Event....");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
>>>>>>> 7ff8864f16ef6f4da62b29cbcb3e09ffadf5c3f7
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
<<<<<<< HEAD
        try{
            progDialog.dismiss();
            Toast.makeText(this, "Data changed!", Toast.LENGTH_LONG).show();
        } catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
=======
        progDialog.dismiss();
        switch (statusCode) {
            case 201:
                button_save.setEnabled(false);
                Toast.makeText(AddEventActivity.this, "You are added a Event", Toast.LENGTH_LONG).show();
                break;

            case 403:
                Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
>>>>>>> 7ff8864f16ef6f4da62b29cbcb3e09ffadf5c3f7
        }
    }

    @Override
    public void onError(Error err) {
<<<<<<< HEAD
        try{
            progDialog.cancel();
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
        } catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
=======
        progDialog.cancel();
        Toast.makeText(this, err.getMessage(), Toast.LENGTH_SHORT).show();
>>>>>>> 7ff8864f16ef6f4da62b29cbcb3e09ffadf5c3f7
    }
}
