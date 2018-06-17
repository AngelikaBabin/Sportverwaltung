package com.example.cora.sportverwaltungveranstalter.activity.events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.BaseActivity;
import com.example.cora.sportverwaltungveranstalter.activity.base.ExposingActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Sportart;

/**
 * @babin GUI
 */
public class AddEventActivity extends ExposingActivity implements AsyncTaskHandler,  {
    EditText editText_title, editText_date, editText_location, editText_participators, editText_details;
    Spinner spinner_sports;
    Button button_save, button_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        initComponents();
        fillSpinner();
        registerEventhandlers();
    }

    private void initComponents() {
        editText_title = (EditText)findViewById(R.id.editText_title);
        spinner_sports = (Spinner)findViewById(R.id.spinner_sportarten);
        editText_date = (EditText)findViewById(R.id.editText_date);
        editText_location = (EditText)findViewById(R.id.editText_location);
        editText_participators = (EditText)findViewById(R.id.editText_maxParticipators);
        editText_details = (EditText)findViewById(R.id.editText_details);
        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_save = (Button)findViewById(R.id.button_save);
    }

    private void registerEventhandlers(){
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddEventActivity.this, MyEventsActivity.class));
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //KRASCHL Speichern der eingegebenen Daten in die DB
            }
        });
    }

    private void fillSpinner(){
        ArrayAdapter<Sportart> adapterSportarten = new ArrayAdapter<Sportart>(this, android.R.layout.activity_list_item, Sportart.values());
        this.spinner_sports.setAdapter(adapterSportarten);
    }

    
    @Override
    public void onPreExecute() {

    }

    @Override
    public void onSuccess(int statusCode, String content) {

    }

    @Override
    public void onError(Error err) {

    }
}
