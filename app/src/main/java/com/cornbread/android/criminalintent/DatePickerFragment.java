package com.cornbread.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

//A second fragment that will be managed by CrimePagerActivity
public class DatePickerFragment extends DialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title) //Title of Dialog
                .setPositiveButton(android.R.string.ok,null) //Positive Button: what the user should press to accept what the dialog presents
                .create(); //"Commits" to creating the dialog
    }
}
