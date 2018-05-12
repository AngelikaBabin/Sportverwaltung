package com.example.cora.sportverwaltung.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.businesslogic.DatabaseConnection;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.example.cora.sportverwaltung.businesslogic.misc.Filter;
import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_Filter = "filter";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Filter filter;

    private DatabaseConnection connection;

    private OnFragmentInteractionListener mListener;

    ListView listView_events;
    View view;

    FragmentManager manager;

    public EventsFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment EventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsFragment newInstance(String param1, String param2, Filter Select) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString("FILTER", Select.toString());
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

    public void setLists(){
        try {
            ArrayList<Veranstaltung> result = connection.getEvents(filter);
            setAdapterData(result);
        }
        catch(Exception ex){
            Toast.makeText(getActivity(),ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void registerEventhandlers() {
        listView_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (filter) {
                    case ALL:
                        intent = new Intent(getActivity(), InfoAllEventsActivity.class);
                        intent.putExtra("event", (new Gson().toJson(listView_events.getSelectedItem())));
                        startActivity(intent);
                        break;
                    case CURRENT:
                        startActivity(new Intent(getActivity(), InfoMyEventsActivity.class));
                        break;
                    case PAST:
                        startActivity(new Intent(getActivity(), InfoPastEventsActivity.class));
                        break;
                }
            }
        });
    }

    private void setAdapterData(ArrayList<Veranstaltung> entries) {
        ArrayAdapter<Veranstaltung> adapter = new ArrayAdapter<Veranstaltung>(getActivity(), android.R.layout.simple_list_item_1, entries);
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
}
