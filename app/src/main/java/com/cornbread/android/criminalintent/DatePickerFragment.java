package com.cornbread.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import org.joda.time.DateTime;

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

    //a dialog is not the same as a fragment
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title) //Title of Dialog
                .setPositiveButton(android.R.string.ok, null) //Positive Button: what the user should press to accept what the dialog presents
                .create(); //"Commits" to creating the dialog
    }
}
