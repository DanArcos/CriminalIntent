package com.cornbread.android.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import java.util.UUID;

//Fragment Activity is support class versus regular Fragment
public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        //return new CrimeFragment(); //We will attach CrimeFragment to this host activity

        //Retrieve the intent at the activity level
        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID); //Retrieve ID from CrimeListFragment

        //Pass down intent info to fragment. The fragment never communicates with the hosting activity. This way it can be reused with other hosting activities.
        return CrimeFragment.newInstance(crimeId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //All the fragment transaction code (SingleFragmentActivity) should precede this.
    }
}
