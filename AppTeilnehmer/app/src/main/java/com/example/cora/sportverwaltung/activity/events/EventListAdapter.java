package com.example.cora.sportverwaltung.activity.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.cora.sportverwaltung.R;

import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;



public class EventListAdapter extends BaseAdapter {
    private ArrayList<Veranstaltung> singleRow;
    private LayoutInflater thisInflator;

    public EventListAdapter(Context context, ArrayList<Veranstaltung> aRow)
    {
        this.singleRow = aRow;
        this.thisInflator = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return singleRow.size();
    }

    @Override
    public Object getItem(int position) {
        return singleRow.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
             convertView = thisInflator.inflate(R.layout.row_layout, parent, false);

             TextView HeadingText = (TextView) convertView.findViewById(R.id.textHeading);
             TextView Participator = (TextView) convertView.findViewById(R.id.textParticipators);
             ImageView typeImage = (ImageView) convertView.findViewById(R.id.sporttypeImage);

             Veranstaltung currentVeranstaltung = (Veranstaltung)getItem(position);

             HeadingText.setText(currentVeranstaltung.getName());
             Participator.setText(currentVeranstaltung.getMaxTeilnehmer());
             //ImageView.setImageResource(...);
        }
        return convertView;
    }
}
