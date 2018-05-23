package com.example.cora.sportverwaltung.activity.events;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.businesslogic.connection.DatabaseConnection;
import com.example.cora.sportverwaltung.businesslogic.data.Location;
import com.example.cora.sportverwaltung.businesslogic.data.Sportart;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstalter;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.example.cora.sportverwaltung.businesslogic.misc.Filter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {
    private static final String ARG_Filter = "filter";

    private Filter filter;

    private DatabaseConnection connection;

    private OnFragmentInteractionListener mListener;

    private ListView listView_events;
    private View view;

    private FragmentManager manager;

    public EventsFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment EventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsFragment newInstance(Filter filter) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString("FILTER", filter.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filter = Filter.valueOf(getArguments().getString("FILTER"));
        }
        connection = DatabaseConnection.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        getViewElements();
        registerEventhandlers();
        manager = getFragmentManager();
        setLists();
        return view;
    }

    private void getViewElements() {
        listView_events = view.findViewById(R.id.listView_events);
    }

    public void setLists() {
        try {
            //ArrayList<Veranstaltung> result = connection.getEvents(filter);
            ArrayList<Veranstaltung> result = createTestData(filter);
            setAdapterData(result);
        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<Veranstaltung> createTestData(Filter filter) {
        ArrayList<Veranstaltung> result = new ArrayList<Veranstaltung>();
        switch(filter){
            case ALL:
                result.add(new Veranstaltung(1,"Meisterschaft", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.SCHWIMMSPORT, null, 30,40));
                result.add(new Veranstaltung(2,"Event", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.BALLSPORT, null, 30,40));
                result.add(new Veranstaltung(3,"Fußball", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.BALLSPORT, null, 30,40));
                result.add(new Veranstaltung(4,"Basketball", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.RENNSPORT, null, 30,40));
                result.add(new Veranstaltung(5,"Schwimmen", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.RENNSPORT, null, 30,40));
                result.add(new Veranstaltung(1,"Meisterschaft", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.KAMPFSPORT, null, 30,40));
                result.add(new Veranstaltung(2,"Event", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.BALLSPORT, null, 30,40));
                result.add(new Veranstaltung(3,"Fußball", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.KAMPFSPORT, null, 30,40));
                result.add(new Veranstaltung(4,"Basketball", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.RENNSPORT, null, 30,40));
                result.add(new Veranstaltung(5,"Schwimmen", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.SCHWIMMSPORT, null, 30,40));
                result.add(new Veranstaltung(1,"Meisterschaft", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.KAMPFSPORT, null, 30,40));
                result.add(new Veranstaltung(2,"Event", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.BALLSPORT, null, 30,40));
                result.add(new Veranstaltung(3,"Fußball", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.SCHWIMMSPORT, null, 30,40));
                result.add(new Veranstaltung(4,"Basketball", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.KAMPFSPORT, null, 30,40));
                result.add(new Veranstaltung(5,"Schwimmen", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.BALLSPORT, null, 30,40));
                break;
            case CURRENT:
                result.add(new Veranstaltung(1,"Meisterschaft", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.KAMPFSPORT, null, 30,40));
                result.add(new Veranstaltung(2,"Event", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.KAMPFSPORT, null, 30,40));
                result.add(new Veranstaltung(5,"Schwimmen", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.BALLSPORT, null, 30,40));
                break;
            case PAST:
                result.add(new Veranstaltung(101,"Meisterschaft 2010", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.BALLSPORT, null, 30,40));
                result.add(new Veranstaltung(102,"Meisterschaft 2011", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.RENNSPORT, null, 30,40));
                result.add(new Veranstaltung(103,"Meisterschaft 2012", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.KAMPFSPORT, null, 30,40));
                result.add(new Veranstaltung(104,"Meisterschaft 2013", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.BALLSPORT, null, 30,40));
                result.add(new Veranstaltung(105,"Meisterschaft 2014", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.BALLSPORT, null, 30,40));
                result.add(new Veranstaltung(101,"Meisterschaft 2015", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.SCHWIMMSPORT, null, 30,40));
                result.add(new Veranstaltung(102,"Meisterschaft 2016", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.SCHWIMMSPORT, null, 30,40));
                result.add(new Veranstaltung(103,"Meisterschaft 2017", "beschreibung der veranstaltung", null, new Location("Oslo", 10,10), Sportart.KLETTERSPORT, null, 30,40));
                break;
        }

        return result;
    }

    private void registerEventhandlers() {
        listView_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                switch (filter) {
                    case ALL:
                        intent = new Intent(getActivity(), InfoAllEventsActivity.class);
                        break;
                    case CURRENT:
                        intent = new Intent(getActivity(), InfoMyEventsActivity.class);
                        break;
                    case PAST:
                        intent = new Intent(getActivity(), InfoPastEventsActivity.class);
                        break;
                }
                String json = (new Gson().toJson(new Veranstaltung(1,"Meisterschaft", "beschreibung der veranstaltung", new Veranstalter("nico.kandut@gmail.com", "Nico INC.", "nk"), new Location("Oslo", 10,10), Sportart.BALLSPORT, new Date(20180508), 30,40)));
                intent.putExtra("event", json);
                startActivity(intent);
            }
        });
    }

    private void setAdapterData(ArrayList<Veranstaltung> entries) {
        EventListAdapter adapter = new EventListAdapter(getActivity(),entries);
        //ArrayAdapter<Veranstaltung> adapter = new ArrayAdapter<Veranstaltung>(getActivity(), android.R.layout.simple_list_item_1, entries);
        listView_events.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //Testing without DatabaseConnection (uncomment)

//    private ArrayList<Veranstaltung> createTestData()
//    {
//        ArrayList<Veranstaltung> testlist = new ArrayList<Veranstaltung>();
//        Veranstaltung v;
//
//        v = new Veranstaltung(1,"Kristian", "1", null, new Location("Oslo", 10,10), Sportart.BALLSPORT, null, 30,40);
//        testlist.add(v);
//
//        v = new Veranstaltung(2,"Cora", "Test2", null, new Location("Moskau", 10,10), Sportart.RENNSPORT, null, 30,40);
//        testlist.add(v);
//
//        v = new Veranstaltung(3,"Nico", "Test3", null, new Location("Klagenfurt", 10,10), Sportart.RENNSPORT, null, 30,40);
//        testlist.add(v);
//
//        return testlist;
//    }
}
