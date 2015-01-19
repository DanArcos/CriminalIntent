package com.cornbread.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

//Abstract class used to take care of fragment transactions already.
//The only required by the user is to instantiate new Fragments
public abstract class SingleFragmentActivity extends FragmentActivity{
    protected abstract Fragment createFragment(); //Create new fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        /*  Fragments are identified by the resource ID of its container layout. In this case, R.id.fragmentContainer should
            contain the fragment, and is not the fragment itself fragmentManager.findFragmentById(R.id.fragmentContainer)
            should return the CrimeFragment() if it exists */
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);

        //If there is no fragment, create one
        if(fragment == null){
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment) //(container view ID, fragment)
                    .commit();
        }
    }
}
