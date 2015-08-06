package com.edu.sihanghuang.dada;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class EventFragment extends Fragment implements AbsListView.OnItemClickListener, LocationListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private LocationManager mLocationManger;
    private String mProvider;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private EventListViewAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAdapter = new EventListViewAdapter(getActivity(), null);

        mLocationManger = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!mLocationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showSettingsAlert();

            return;
        }

        if (!mLocationManger.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Log.e("", "network provider is disabled");
            return;
        }

        Location location = getLastLocation();

        if (location != null) {
            pullDataFromServerAroundCenter(location);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationManger.removeUpdates(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Helper

    /**
     * Function to show settings alert dialog
     * */
    public void showSettingsAlert(){
        final Context mContext = getActivity();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private Location getLastLocation() {
        Location location = mLocationManger.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location == null) {
            location = mLocationManger.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        return location;
    }

    private void requestLocationUpdates() {
        mLocationManger.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1, this);
        mLocationManger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
    }

    private void pullDataFromServerAroundCenter(Location location) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(APIConstants.EventParseClassName);
//        query
    }

    // Listener

    @Override
    public void onLocationChanged(Location location) {
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());
        String string = "lat is " + Integer.toString(lat) + ", " + "lng is " + Integer.toString(lng);
        Log.i("", string);
    }

    public void onStatusChanged (String provider, int status, Bundle extras) {

    }

    public void onProviderDisabled (String provider) {
        Log.e("", "location provider disabled");
    }

    public void onProviderEnabled (String provider) {

    }

    // Actions

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
//            mListener.onEventFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onEventFragmentInteraction(String id);
    }

    // Custom Adapter

    private class EventListViewAdapter extends ArrayAdapter<List<ParseObject>> {

        private Context context;
        private List<ParseObject> objects;

        public EventListViewAdapter (Context context, List<ParseObject> objects) {
            super(context, -1);
            this.context = context;
            this.objects = objects == null ? new ArrayList<ParseObject>() : objects;
        }

        @Override
        public int getCount() {
            return this.objects.size();
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (android.view.LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.event_list_item_layout,parent);
            }

            ParseObject object = this.objects.get(position);
            TextView eventNameTextView = (TextView)convertView.findViewById(R.id.eventNameTextView);
            eventNameTextView.setText(((String)object.get(APIConstants.EventNameKey)));

            TextView eventDateTextView = (TextView)convertView.findViewById(R.id.eventDateTextView);
            eventDateTextView.setText("Fake Date");

            TextView eventLocalTextView = (TextView)convertView.findViewById(R.id.eventLocationTextView);
            eventLocalTextView.setText(((String)(object.get(APIConstants.EventLocationKey))));

            TextView eventDescriptionTextView = (TextView)convertView.findViewById(R.id.eventDescriptionTextView);
            eventDescriptionTextView.setText(((String)(object.get(APIConstants.EventContentKey))));

            return null;
        }
    }

}
