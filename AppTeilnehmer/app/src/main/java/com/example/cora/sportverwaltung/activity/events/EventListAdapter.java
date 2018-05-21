package com.example.cora.sportverwaltung.activity.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cora.sportverwaltung.R;
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
             TextView HeadingText = convertView.findViewById(R.id.textView_Heading);
             TextView Date = convertView.findViewById(R.id.textView_Date);
             //TextView Location = (TextView) convertView.findViewById(R.id.textLocation);
             ImageView typeImage = convertView.findViewById(R.id.imageView_sport);

             Veranstaltung currentVeranstaltung = (Veranstaltung)getItem(position);

             HeadingText.setText(String.valueOf(currentVeranstaltung.getName()));
             //Date.setText(currentVeranstaltung.getDatetime().toString());
             //Location.setText(String.valueOf(currentVeranstaltung.getLocation().getName()));

             switch(currentVeranstaltung.getSportart())
             {
                 case BALLSPORT:
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
