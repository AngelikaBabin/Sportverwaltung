package com.example.cora.sportverwaltung.activity.account;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.BaseActivity;
import com.example.cora.sportverwaltung.businesslogic.data.Teilnehmer;

public class ProfileActivity extends BaseActivity {

    TextView textView_name;
    TextView textView_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // display layout
            setContent(R.layout.activity_profile);

            // Teilnehmer t = connection.getTeilnehmer();
            Teilnehmer t = new Teilnehmer("kraschlc@edu.htl-villach.at", "ChristophKraschl", "ck", 110);

            fillIn(t);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void fillIn(Teilnehmer t) {
        textView_name = findViewById(R.id.textView_name);
        textView_email = findViewById(R.id.textView_email);

        textView_email.setText(t.getName());
        textView_email.setText(t.getEmail());
    }
}
