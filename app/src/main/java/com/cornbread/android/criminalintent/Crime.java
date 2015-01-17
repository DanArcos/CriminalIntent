package com.cornbread.android.criminalintent;

import java.util.UUID;

public class Crime {
    private UUID mId; //UUID are unique IDs to identify the crime object
    private String mTitle; //String to name crime object

    public Crime(){
        //Generate Unique Identifier
        mId = UUID.randomUUID();
    }

    //We only create a getter for the UUID
    public UUID getId(){
        return mId;
    }

    //Getter for title
    public String getTitle(){
        return mTitle;
    }

    //Setter for title
    public void setTitle(String title){
        mTitle = title;
    }
}
