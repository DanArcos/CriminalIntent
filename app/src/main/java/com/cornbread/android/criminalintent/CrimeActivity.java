package com.cornbread.android.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

//Fragment Activity is support class versus regular Fragment
public class CrimeActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        //Fragments are identified by the resource ID of its container layout
        //In this case, R.id.fragmentContainer should contain the fragment, and is not the fragment itself
        // fragmentManager.findFragmentById(R.id.fragmentContainer) should return the CrimeFragment() if it exists
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);

        //If there is no fragment, create one
        if(fragment == null){
            fragment = new CrimeFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment) //(container view ID, fragment)
                    .commit();
        }
    }
}
