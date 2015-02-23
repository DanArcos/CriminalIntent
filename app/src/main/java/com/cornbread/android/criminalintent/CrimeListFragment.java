package com.cornbread.android.criminalintent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CrimeListFragment extends ListFragment{
    private static final String TAG = "CrimeListFragment";

    private ArrayList<Crime> mCrimes;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //Add Options Menu at the top

        getActivity().setTitle(R.string.crimes_title);

        mCrimes = CrimeLab.get(getActivity()).getCrimes(); // CrimeLab.get() to "Access" crime lab/. Then .getCrimes() to get activities.

        //Create Array Adapter
        //Parameter 1: getActivity() == CrimeListActivity.java (Class)
        //Parameter 2: choose format that list will display in (android.R.layout.simple_list_item)
        //Parameter 3: Choose data this will be used to populate list (mCrime)
        //ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(),android.R.layout.simple_list_item_1,mCrimes);
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);

        setRetainInstance(true);
        mSubtitleVisible = false; //Set false as default value since subtitle will not be visible on launch
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        Button mAddCrime = (Button)v.findViewById(R.id.add_crime_button);
        mAddCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCrime();
            }
        });

        //If honeycomb or above and subtitle visible when onCreateView called -> set subtitle to subtitle string
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(mSubtitleVisible){
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
            }
        }

        //This registers each list item for the context menu
        ListView listView = (ListView)v.findViewById(android.R.id.list);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            //Use floating  context menus on Froyo and Gingerbread
            registerForContextMenu(listView);
        } else {
            //Use contextual action bar on Honeycomb and higher
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                    //Required but not used here
                }

                //ActionMode.Callback methods
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    //Called when ActionMode is created.
                    //Inflate Context menu resource to be displayed in contextual action bar
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.crime_list_item_context, menu);

                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    //Called after onCreateActionMode and when existing contextual action bar need to be refreshed

                    //Required but not used here
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    //Called when user selects an action

                    switch(item.getItemId()){
                        case R.id.menu_item_delete_crime:
                            CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
                            CrimeLab crimeLab = CrimeLab.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i-- ){
                                if(getListView().isItemChecked(i)){
                                    crimeLab.deleteCrime(adapter.getItem(i));
                                }
                            }

                            mode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    //Called when ActionMode is about to be destroyed bc of user cancellation
                }
            });
        }

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Crime c = (Crime)(getListAdapter().getItem(position)); //Pull crime by its position in the list.
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
        //Log.d(TAG, c.getTitle()+" was clicked.");

        //Start CrimePagerActivity with the given crime, we have removed CrimeActivity from the picture
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);

        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {
        public CrimeAdapter(ArrayList<Crime> crimes){
            super(getActivity(),0,crimes); //We pass 0 as the layout since we won't be using a predefined ID
        }

        @Override //getView allows us to set our own custom layout design.
        public View getView(int position, View convertView, ViewGroup parent) {
            //If we weren't given a view, inflate one.
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime,null);
            }

            //Configure the view for this Crime
            Crime c = getItem(position);

            //Set title UI widget
            TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());

            //Set Date UI textView widget
            TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(c.formatDate());

            //Set Checkbox UI widget
            CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }

    @Override //We override onResume() and not onstart since we aren't sure that onStart will get called
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //Inflate menu from resource credit
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible && showSubtitle != null){
            //Boolean is true and showSubtitle Menu item exists -> set string to hide subtitle string
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            //If newCrime menu item hit, add a new crime
            case R.id.menu_item_new_crime:
                addNewCrime(); //??WHY??
                return true;

            //When you click the subtitle menu item...
            case R.id.menu_item_show_subtitle:
                //If no subtitle
                if(getActivity().getActionBar().getSubtitle() == null){
                    getActivity().getActionBar().setSubtitle(R.string.subtitle); //Set subtitle
                    mSubtitleVisible = true; //Change flag
                    item.setTitle(R.string.hide_subtitle); //Change menu item text
                } else { //If subtitle
                    getActivity().getActionBar().setSubtitle(null); //Take away subtitle
                    mSubtitleVisible = false; //Change flag
                    item.setTitle(R.string.show_subtitle); //Change menu item text
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //All the following is used to get a handle of the crime in question
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo(); //Get info regarding item that has been long pressed
        int position = info.position; //Get position of item
        CrimeAdapter adapter = (CrimeAdapter)getListAdapter(); //Get an instance of the crime adapter
        Crime crime = adapter.getItem(position); //Use adapter to FINALLY retrieve the crime

        switch(item.getItemId()){
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).deleteCrime(crime);
                adapter.notifyDataSetChanged();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    public void addNewCrime(){
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);

        Intent i = new Intent(getActivity(), CrimePagerActivity.class); //Intent to load activity with new Crime
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivityForResult(i,0);
    }
}
