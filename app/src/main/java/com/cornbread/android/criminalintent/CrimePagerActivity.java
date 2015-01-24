package com.cornbread.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class CrimePagerActivity extends FragmentActivity{
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this); //Instantiate the ViewPager object
        mViewPager.setId(R.id.viewPager); //Attach Id to ViewPager
        setContentView(mViewPager); // "Inflate" layout

        mCrimes = CrimeLab.get(this).getCrimes(); //Retrieve data set

        FragmentManager fragmentManager = getSupportFragmentManager(); //Get Instance of fragmentManager

        //Below is the code that will let ViewPager work with the fragment manager to attach fragments
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public int getCount() {
                return mCrimes.size(); //Return the number of items in the array list
            }

            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId()); //Uses crimeId to fetch a crime and return a proper fragment
            }
        });
    }
}
