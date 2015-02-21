package com.cornbread.android.criminalintent;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

public class Crime {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";

    private UUID mId; //UUID are unique IDs to identify the crime object
    private String mTitle; //String to name crime object
    private MutableDateTime mDate; //Date that the crime occurred
    private boolean mSolved; //Flag whether or not the crime has been solved

    public Crime(){
        //Generate Unique Identifier
        mId = UUID.randomUUID();
        mDate = new MutableDateTime();
    }

    //New constructor to pass json object
    public Crime(JSONObject json) throws JSONException{
        mId = UUID.fromString(json.getString(JSON_ID));
        if(json.has(JSON_TITLE)){
            mTitle = json.getString(JSON_TITLE);
        }
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new MutableDateTime(json.getLong(JSON_DATE));
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate);
        return json;
    }

    @Override //Display title not object name
    public String toString() {
        return mTitle;
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

    public MutableDateTime getDate(){
        return mDate;
    }

    public void setDate (MutableDateTime date){
        mDate = date;
    }

    public String formatDate(){
        String dateOutput;
        dateOutput = formatDayOfWeek(getDate().getDayOfWeek())+ ", ";
        dateOutput += formatMonthOfYear(getDate().getMonthOfYear())+ " ";
        dateOutput += getDate().getDayOfMonth() + ", ";
        dateOutput += getDate().getYear();

        return dateOutput;
    }

    public String formatDayOfWeek(int day){
        String weekDay;

        switch (day){
            case 1:  weekDay = "Monday";
                break;
            case 2:  weekDay = "Tuesday";
                break;
            case 3:  weekDay = "Wednesday";
                break;
            case 4:  weekDay = "Thursday";
                break;
            case 5:  weekDay = "Friday";
                break;
            case 6:  weekDay = "Saturday";
                break;
            case 7:  weekDay = "Sunday";
                break;
            default:  weekDay = "Invalid Day of week";
                break;
        }
        return weekDay;
    }

    public boolean isSolved(){
        return mSolved;
    }

    public void setSolved(boolean solved){
        mSolved = solved;
    }

    public String formatMonthOfYear(int month){
        String monthOfYear;

        switch(month){
            case 1:  monthOfYear = "Jan";
                break;
            case 2:  monthOfYear = "Feb";
                break;
            case 3:  monthOfYear = "March";
                break;
            case 4:  monthOfYear = "April";
                break;
            case 5:  monthOfYear = "May";
                break;
            case 6:  monthOfYear = "June";
                break;
            case 7:  monthOfYear = "July";
                break;
            case 8:  monthOfYear = "Aug";
                break;
            case 9:  monthOfYear = "Sept";
                break;
            case 10:  monthOfYear = "Oct";
                break;
            case 11:  monthOfYear = "Nov";
                break;
            case 12:  monthOfYear = "Dec";
                break;
            default:  monthOfYear = "Invalid Day of week";
                break;
        }

        return monthOfYear;
    }

    public String getTime(){
        int hour = mDate.getHourOfDay();
        int minute = mDate.getMinuteOfHour();

        if(minute < 10){
            return hour + ":0" + minute;
        }
        else {return hour + ":" + minute;}

    }
}
