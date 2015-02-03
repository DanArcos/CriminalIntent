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

public class ChooseDialogFragment extends DialogFragment{
    public static final String EXTRA_CHOICE = "com.cornbread.android.criminalintent.choice";

    private int mChoice;

    private void sendResult(int resultCode){
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_CHOICE, mChoice);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.choose_dialog, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_chooser_title)
                .setPositiveButton(R.string.dialog_chooser_date , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mChoice = 1;
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(R.string.dialog_chooser_time, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mChoice = 2;
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
}
