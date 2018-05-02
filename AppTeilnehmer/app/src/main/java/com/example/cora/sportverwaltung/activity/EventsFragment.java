package com.example.cora.sportverwaltung.activity;

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

import com.example.cora.sportverwaltung.R;


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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    ListView listView_events;
    Button button_allEvents, button_myEvents, button_pastEvents;

    View view;

    FragmentManager manager;

    public EventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsFragment newInstance(String param1, String param2, String Select) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        getViewElements();
        registerEventhandlers();
        manager = getFragmentManager();
        setAdapterData(getResources().getStringArray(R.array.test_array_all));
        setOnItemClickListener(new AllEventsFragment());
        return view;
    }

    private void getViewElements() {
        listView_events = view.findViewById(R.id.listView_events);
        button_allEvents = (Button) view.findViewById(R.id.button_allEvents);
        button_myEvents = (Button) view.findViewById(R.id.button_myEvents);
        button_pastEvents = (Button) view.findViewById(R.id.button_pastEvents);
    }

    private void registerEventhandlers() {
        button_allEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            setAdapterData(getResources().getStringArray(R.array.test_array_all));
            setOnItemClickListener(new AllEventsFragment());
            }
        });

        button_myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAdapterData(getResources().getStringArray(R.array.test_array_toDo));
                setOnItemClickListener(new MyEventsFragment());
            }
        });

        button_pastEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAdapterData(getResources().getStringArray(R.array.test_array_done));
                setOnItemClickListener(new PastEventsFragment());
            }
        });

        /*listView_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyEventsFragment myEventsFragment = new MyEventsFragment();
                manager.beginTransaction().replace(
                        R.id.ConstraintLayout_for_fragment,
                        myEventsFragment,
                        myEventsFragment.getTag()
                ).commit();
            }
        });*/
    }

    private void setOnItemClickListener(final Fragment myFragment){
        listView_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = myFragment;
                manager.beginTransaction().replace(
                        R.id.ConstraintLayout_for_fragment,
                        fragment,
                        fragment.getTag()
                ).commit();
            }
        });
    }

    private void setAdapterData(String[] entries){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 ,entries);
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
