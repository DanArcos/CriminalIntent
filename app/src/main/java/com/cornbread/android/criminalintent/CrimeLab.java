package com.cornbread.android.criminalintent;

import android.content.Context;

//Crime Lab is part of the model that will store our crimes together
public class CrimeLab {
    private static CrimeLab sCrimeLab; //The s prefix indicates that this is a static variable
    private Context mAppContext;

    private CrimeLab (Context appContext){
        mAppContext = appContext;
    }

    /*  Think of this like a "CrimeLab getCrimeLab()" type of method.
        Here to return the crimelab we need to pass a context parameter.
        It will return the already existing crime lab or create a new one. */
    public static CrimeLab get(Context c){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }
}
