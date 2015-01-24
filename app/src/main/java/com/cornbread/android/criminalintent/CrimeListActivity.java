package com.cornbread.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

//Activity to host CrimeList fragment
public class CrimeListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment(); //We will attach CrimeListFragment to this host activity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //All the fragment transaction code from SingleFragmentActivity precedes this comment.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crime_list, menu);
        return true;
    }
}
