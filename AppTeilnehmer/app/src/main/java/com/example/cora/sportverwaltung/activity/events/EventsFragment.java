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
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod.GET;

/**
 * @kandut async listener and code cleanup
 * @kumnig logical structure, gui structure different webservice calls
 * @rajic fragmentmanager related stuff, searching, filtering and gui design
 */

public class EventsFragment extends Fragment implements AsyncTaskHandler {

    // UI references
    private ProgressDialog progDialog;
    private ListView listView_events;
    private EditText editText_search;
    private TextView textView_message;
    private View view;

    private Filter filter;
    private ArrayList<Veranstaltung> events;

    private OnFragmentInteractionListener interactionListener;

    // needed for android
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

        Bundle arguments = getArguments();
        if (arguments != null) {
            filter = Filter.valueOf(arguments.getString("FILTER"));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.fragment_events, container, false);
            getViewElements();
            registerEventhandlers();

            String queryString = "filter=" + filter;

            AsyncWebserviceTask task = new AsyncWebserviceTask(GET, "events", this, getActivity().getApplicationContext());
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

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Veranstaltung> filteredEvents = new ArrayList<>();

                if (editText_search.getText() == null || editText_search.getText().toString().equals("")) {
                    setAdapterData(events);
                } else {
                    for (Veranstaltung v : events) {
                        Date eventDate = v.getDatetime();
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
                        String strDate = dateFormatter.format(eventDate);
                        if (v.getName().toLowerCase().contains(editText_search.getText().toString().toLowerCase())
                                || v.getLocation().toLowerCase().contains(editText_search.getText().toString().toLowerCase())
                                || strDate.toLowerCase().contains(editText_search.getText().toString().toLowerCase())) {
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

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(this.getActivity());
        progDialog.setMessage("Loading events...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
        switch (statusCode) {
            case 200:
                Type collectionType = new TypeToken<ArrayList<Veranstaltung>>() {
                }.getType();

                events = new Gson().fromJson(content, collectionType);
                setAdapterData(events);

                if (events.size() > 0) {
                    textView_message.setVisibility(View.GONE);
                } else {
                    listView_events.setVisibility(View.GONE);
                }
                break;

            case 400:
                Toast.makeText(getContext(), "Unknown filter", Toast.LENGTH_SHORT).show();

            case 403:
                Toast.makeText(getContext(), "Not logged in", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Error err) {
        progDialog.cancel();
        Toast.makeText(this.getActivity(), err.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
