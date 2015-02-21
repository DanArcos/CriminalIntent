package com.cornbread.android.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class CriminalIntentJSONSerializer {
    private Context mContext;
    private String mFilename;

    //Constructor
    public CriminalIntentJSONSerializer(Context c, String f){
        mContext = c;
        mFilename = f;
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException{
        //Build array in JSON
        JSONArray array = new JSONArray();
        for(Crime c : crimes){
            array.put(c.toJSON()); //Convert crime to JSON and put into JSON array
        }

        //Write the file to disk
        Writer writer = null; //Create writer object
        try{
            OutputStream out = mContext.openFileOutput(mFilename,Context.MODE_PRIVATE); //Prepend filename with path to folder, create and open file for writing
            writer = new OutputStreamWriter(out); //Writer will convert java string to raw bytes
            writer.write(array.toString());
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
