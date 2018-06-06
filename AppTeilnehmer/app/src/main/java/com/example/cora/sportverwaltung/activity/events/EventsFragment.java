package com.example.cora.sportverwaltung.activity.events;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncWebserviceTask;
import com.example.cora.sportverwaltung.businesslogic.data.Veranstaltung;
import com.example.cora.sportverwaltung.businesslogic.misc.Filter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod.GET;

/**
 * @kandut async listener and code cleanup
 * @kumnig logical structure, gui structure different webservice calls
 * @rajic fragmentmanager related stuff, searching, filtering and gui design
 */

public class EventsFragment extends Fragment implements AsyncTaskHandler {
    private static final String ARG_Filter = "filter";
    private ProgressDialog progDialog;

    private Filter filter;

    private OnFragmentInteractionListener mListener;

    private ListView listView_events;
    private View view;
    EditText editText_search;
    ArrayList<Veranstaltung> events;
    private FragmentManager manager;
    TextView textView_message;

    public EventsFragment() {

    }

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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.fragment_events, container, false);
            getViewElements();
            registerEventhandlers();
            manager = getFragmentManager();

            String queryString = "filter=" + filter;

            AsyncWebserviceTask task = new AsyncWebserviceTask(GET, "events", this);
            task.execute(queryString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void getViewElements() {
        listView_events = view.findViewById(R.id.listView_events);
        editText_search = view.findViewById(R.id.editText_search);
        textView_message = view.findViewById(R.id.textView_message);
    }

    private void registerEventhandlers() {
        listView_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
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
                Veranstaltung v = (Veranstaltung) listView_events.getItemAtPosition(position);
                Gson gson = new Gson();
                String json = gson.toJson(v, Veranstaltung.class);
                intent.putExtra("event", json);
                startActivity(intent);
            }
        });

        editText_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Veranstaltung> filteredEvents = new ArrayList<>();

                if(editText_search.getText().equals("") || editText_search.getText() == null)
                {
                    setAdapterData(events);
                }
                else
                {
                    for(Veranstaltung v : events)
                    {
                        if(v.getName().contains(editText_search.getText()))
                        {
                            filteredEvents.add(v);
                        }
                    }
                    setAdapterData(filteredEvents);
                }
            }
        });
    }

    private void setAdapterData(ArrayList<Veranstaltung> entries) {
        EventListAdapter adapter = new EventListAdapter(getActivity(), entries);
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

    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(this.getActivity());
        progDialog.setMessage("Logging in...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
        Type collectionType = new TypeToken<ArrayList<Veranstaltung>>() {
        }.getType();

        events = new Gson().fromJson(content, collectionType);
        setAdapterData(events);

        if (events.size() > 0) {
            textView_message.setVisibility(View.INVISIBLE);
        } else {
            textView_message.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        Toast.makeText(this.getActivity(), err.getMessage(), Toast.LENGTH_SHORT).show();
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
