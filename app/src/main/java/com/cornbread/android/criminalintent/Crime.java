package com.cornbread.android.criminalintent;

import org.joda.time.DateTime;

import java.util.UUID;

public class Crime {
    private UUID mId; //UUID are unique IDs to identify the crime object
    private String mTitle; //String to name crime object
    private DateTime mDate; //Date that the crime occurred
    private boolean mSolved; //Flag whether or not the crime has been solved

    public Crime(){
        //Generate Unique Identifier
        mId = UUID.randomUUID();
        mDate = new DateTime();
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

    public DateTime getDate(){
        return mDate;
    }

    public void setDate (DateTime date){
        mDate = date;
    }

    public boolean isSolved(){
        return mSolved;
    }

    public void setSolved(boolean solved){
        mSolved = solved;
    }
}
