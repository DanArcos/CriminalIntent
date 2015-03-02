package com.cornbread.android.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo {
    private static final String JSON_FILENAME = "filename";

    private String mFileName;

    //Create a photo representing an existing file on disk

    //Constructor with filename string as parameter
    public Photo(String filename){
        mFileName = filename;
    }

    //Constructor with json object as parameter. The crime class will use this.
    public Photo(JSONObject json) throws JSONException{
        mFileName = json.getString(JSON_FILENAME);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME, mFileName);
        return json;
    }

    public String getFileName(){
        return mFileName;
    }
}
