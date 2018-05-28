package com.example.cora.sportverwaltung.activity.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.businesslogic.data.Sportart;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;

import java.util.ArrayList;



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
            TextView headingText = convertView.findViewById(R.id.textView_heading);
            //TextView date = convertView.findViewById(R.id.textView_date);
            TextView location = (TextView) convertView.findViewById(R.id.textView_location);
            ImageView typeImage = convertView.findViewById(R.id.imageView_sport);

            Veranstaltung currentVeranstaltung = (Veranstaltung)getItem(position);

            headingText.setText(String.valueOf(currentVeranstaltung.getName()));
            //date.setText(currentVeranstaltung.getDatetime().toString());
            location.setText(currentVeranstaltung.getLocation());

            switch(Sportart.valueOf(currentVeranstaltung.getSportart().toUpperCase()))
            {
                case BASKETBALL:
                    typeImage.setImageResource(R.drawable.sports_basketball);
                    break;

                case RENNSPORT:
                    typeImage.setImageResource(R.drawable.sports_running);
                    break;
                case KAMPFSPORT:
                    typeImage.setImageResource(R.drawable.sports_boxing_glove);
                    break;

                case KLETTERSPORT:
                    typeImage.setImageResource(R.drawable.sports_climbing);
                    break;

                case SCHWIMMSPORT:
                    typeImage.setImageResource(R.drawable.sports_swimming);
                    break;

                case EXTREMSPORT:
                    typeImage.setImageResource(R.drawable.sports_parachute);
                    break;
                default:
                    typeImage.setImageResource(R.drawable.sports_trophy);
            }

        }
        return convertView;
    }
}
