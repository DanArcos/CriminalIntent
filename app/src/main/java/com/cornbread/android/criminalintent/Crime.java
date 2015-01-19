package com.cornbread.android.criminalintent;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

    public String formatDate(){
        String dateOutput;
        dateOutput = formatDayOfWeek(getDate().getDayOfWeek())+ ", ";
        dateOutput += formatMonthOfYear(getDate().getMonthOfYear())+ " ";
        dateOutput += getDate().getDayOfMonth() + ", ";
        dateOutput += getDate().getYear();

        return dateOutput;
    }

    public boolean isSolved(){
        return mSolved;
    }

    public void setSolved(boolean solved){
        mSolved = solved;
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
}
