package com.example.cora.sportverwaltung.activity.events;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncWebserviceTask;
import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.ExposingActivity;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.google.gson.Gson;
import android.app.ProgressDialog;

import static com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod.DELETE;

/**
 * @kandut async listener and code cleanup
 * @kumnig activity code
 * @rajic gui design
 */

public class InfoMyEventsActivity extends ExposingActivity implements AsyncTaskHandler {
    TextView textView_header, textView_date, textView_place, textView_organizer, textView_details, textView_sport;
    Button button_logout;

    private Veranstaltung selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_my_events);
        //get selected Event data from EventsFragment intent
        selectedEvent = new Gson().fromJson(this.getIntent().getExtras().getString("event"), Veranstaltung.class);
        getViewElements();
        registerEventhandlers();
        setValuesInFields();
    }

    private void setValuesInFields() {
        textView_header.setText(selectedEvent.getName());
        textView_date.setText("Thu 22. May");
        textView_place.setText(selectedEvent.getLocation().toString());
        textView_organizer.setText(selectedEvent.getVeranstalter().toString());
        textView_details.setText(selectedEvent.getDetails());
        textView_sport.setText(selectedEvent.getSportart());
    }

    private void getViewElements() {
        textView_header = findViewById(R.id.textView_header);
        textView_details = findViewById(R.id.textView_details);
        textView_date = findViewById(R.id.textView_date);
        textView_place = findViewById(R.id.textView_place);
        textView_organizer = findViewById(R.id.textView_organizer);
        button_logout = findViewById(R.id.button_logout);
        textView_sport = findViewById(R.id.textView_sport);
    }

    private void registerEventhandlers() {
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AsyncWebserviceTask task = new AsyncWebserviceTask(DELETE, "teilnahme", InfoMyEventsActivity.this);
                    task.execute("eventId=" + selectedEvent.getId(), null);
                    Toast.makeText(InfoMyEventsActivity.this, "You are no longer participating", Toast.LENGTH_LONG).show();
                    button_logout.setEnabled(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(InfoMyEventsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
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
        button_logout.setEnabled(false);
        Toast.makeText(this, "You are now participating", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        Toast.makeText(this, err.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
