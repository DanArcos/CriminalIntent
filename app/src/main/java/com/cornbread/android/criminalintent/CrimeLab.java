package com.cornbread.android.criminalintent;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

//Crime Lab is part of the model that will store our crimes together
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private ArrayList<Crime> mCrimes; //The array list that will store crimes
    private CriminalIntentJSONSerializer mSerializer;

    private static CrimeLab sCrimeLab; //The s prefix indicates that this is a static variable
    private Context mAppContext;

    private CrimeLab (Context appContext){
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
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

    public void addCrime(Crime c){
        //Add a new crime to the crime list array
        mCrimes.add(c);
    }

    public boolean saveCrime(){
        try{
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true; //Return true if files save correctly
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }
}
