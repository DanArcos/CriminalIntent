package com.cornbread.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

//Crime Lab is part of the model that will store our crimes together
public class CrimeLab {
    private ArrayList<Crime> mCrimes; //The array list that will store crimes

    private static CrimeLab sCrimeLab; //The s prefix indicates that this is a static variable
    private Context mAppContext;

    private CrimeLab (Context appContext){
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
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

   public void addCrimes(Crime c){
       //Add a new crime to the crime list array
       mCrimes.add(c);
   }

    public ArrayList<Crime> getCrimes(){
        return mCrimes;
    }

    //Search for crime in array using known ID
    public Crime getCrime(UUID id){
        for (Crime c : mCrimes){
            if(c.getId().equals(id)){
                return c;
            }
        }
        return null;
    }
}
