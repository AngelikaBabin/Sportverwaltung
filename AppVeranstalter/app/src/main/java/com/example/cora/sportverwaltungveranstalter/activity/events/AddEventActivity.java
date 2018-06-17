package com.example.cora.sportverwaltungveranstalter.activity.events;

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
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Sportart;

/**
 * @babin GUI
 */
public class AddEventActivity extends ExposingActivity implements AdapterView.OnItemSelectedListener {
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
            //KRASCHL Speichern der eingegebenen Daten in die DB
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
}
