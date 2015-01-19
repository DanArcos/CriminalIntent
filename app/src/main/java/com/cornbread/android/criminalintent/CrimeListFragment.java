package com.cornbread.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CrimeListFragment extends ListFragment{
    private static final String TAG = "CrimeListFragment";

    private ArrayList<Crime> mCrimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);

        mCrimes = CrimeLab.get(getActivity()).getCrimes(); // CrimeLab.get() to "Access" crime lab/. Then .getCrimes() to get activities.

        //Create Array Adapter
        //Parameter 1: getActivity() == CrimeListActivity.java (Class)
        //Parameter 2: choose format that list will display in (android.R.layout.simple_list_item)
        //Parameter 3: Choose data this will be used to populate list (mCrime)
        //ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(),android.R.layout.simple_list_item_1,mCrimes);
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Crime c = (Crime)(getListAdapter().getItem(position)); //Pull crime by its position in the list.
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
        //Log.d(TAG, c.getTitle()+" was clicked.");

        //Start CrimeActivity
        Intent i = new Intent(getActivity(),CrimeActivity.class);
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
}
