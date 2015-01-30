package com.cornbread.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import org.joda.time.DateTime;

import java.util.GregorianCalendar;

//A second fragment that will be managed by CrimePagerActivity
public class DatePickerFragment extends DialogFragment{
    public static final String EXTRA_DATE = "com.cornbread.android.criminalintent.date"; //String to help store intent extras
    private DateTime mDate; // Date which we will use to store the value of the date info

    public static DatePickerFragment newInstance(DateTime date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date); //Put away date info as key value pairing

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args); //"store arguments"

        return fragment;
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    //a dialog is not the same as a fragment
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (DateTime)getArguments().getSerializable(EXTRA_DATE); //Retrieve DateTime info

        //We must pull out the following so that we ca properly intitialize the datePicker
        int year = mDate.getYear();
        int month = mDate. getMonthOfYear() - 1; //Jan = 0,
        int day = mDate.getDayOfMonth();

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_picker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDate = new DateTime(new GregorianCalendar(year, monthOfYear, dayOfMonth)); //Use selected date to create new mDate

                //Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title) //Title of Dialog
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        }) //Positive Button: what the user should press to accept what the dialog presents
                .create(); //"Commits" to creating the dialog
    }
}
