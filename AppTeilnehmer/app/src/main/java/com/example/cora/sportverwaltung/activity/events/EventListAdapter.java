package com.example.cora.sportverwaltung.activity.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
             TextView Date = (TextView) convertView.findViewById(R.id.textDate);
             TextView Location = (TextView) convertView.findViewById(R.id.textLocation);
             ImageView typeImage = (ImageView) convertView.findViewById(R.id.sporttypeImage);

             Veranstaltung currentVeranstaltung = (Veranstaltung)getItem(position);

             HeadingText.setText(String.valueOf(currentVeranstaltung.getName()));
             //Date.setText(currentVeranstaltung.getDatetime().toString());
             Location.setText(String.valueOf(currentVeranstaltung.getLocation().getName()));

             switch(currentVeranstaltung.getSportart())
             {
                 case BALLSPORT:
                     typeImage.setImageResource(R.drawable.icons8_basketball);
                     break;

                 case RENNSPORT:
                     typeImage.setImageResource(R.drawable.icons8_running);
                     break;
                 case KAMPFSPORT:
                     typeImage.setImageResource(R.drawable.icons8_boxing_glove);
                     break;

                 case KLETTERSPORT:
                     typeImage.setImageResource(R.drawable.icons8_climbing);
                     break;

                 case SCHWIMMSPORT:
                     typeImage.setImageResource(R.drawable.icons8_swimming);
                     break;

                 case EXTREMSPORT:
                     typeImage.setImageResource(R.drawable.icons8_parachute);
                     break;
                 default:
                     typeImage.setImageResource(R.drawable.icons8_trophy);
             }

        }
        return convertView;
    }
}
