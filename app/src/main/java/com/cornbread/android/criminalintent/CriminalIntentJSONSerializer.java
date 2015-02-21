package com.cornbread.android.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    //loadCrimes to return an array list consisting of crime objects
    public ArrayList<Crime> loadCrimes() throws IOException, JSONException{
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try{
            //Open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while( (line = reader.readLine()) != null ){
                //Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            //Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            //Build the array of crimes from JSONObjects
            for(int i = 0; i < array.length(); i++){
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e){
            //Ignore
        } finally {
            if(reader != null)
                reader.close(); //free up reader if error occurs
        }
        return crimes;
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
