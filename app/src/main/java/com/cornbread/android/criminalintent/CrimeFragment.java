package com.cornbread.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

//Notice this fragment class takes from the support library!
public class CrimeFragment extends Fragment{
    private Crime mCrime; //Crime object

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }
}
