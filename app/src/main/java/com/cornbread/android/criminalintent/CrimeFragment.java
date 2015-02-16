package com.cornbread.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

import java.util.UUID;

//Notice this fragment class takes from the support library!
public class CrimeFragment extends Fragment{
    public static final String EXTRA_CRIME_ID = "com.cornbread.android.criminalintent.crime_id";

    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final String DIALOG_CHOOSE = "choose";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private static final int REQUEST_CHOICE = 2;
    private static final int CHOICE_DATE = 1;
    private static final int CHOICE_TIME = 2;

    private Crime mCrime; //Crime object
    private EditText mTitleField;
    private Button mChooseDialogButton;
    private CheckBox mSolvedCheckBox;

    //This is a public function whereas Activity.onCreate is a protected
    //This must be public so that the activity (a different) can call it.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. mCrime = new Crime();
        // 2. UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        //Retrieve arguments from new instance created. Notice: this does not communicate with the hosting activity.
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);

        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    private void updateDateTime(){ mChooseDialogButton.setText( "Time: "+ mCrime.getTime()+". " + mCrime.formatDate()+".");}

    //This function inflates the view
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        //Code for EditText widget
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mCrime.setTitle(c.toString()); //When Text is changed title is changed
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This one too
            }
        });

        //Choose Dialog Button
        mChooseDialogButton = (Button)v.findViewById(R.id.choose_dialog);
        updateDateTime();
        mChooseDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ChooseDialogFragment dialog = new ChooseDialogFragment();
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_CHOICE);
                dialog.show(fm, DIALOG_CHOOSE);
            }
        });

        //Code for CheckBox Widget
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Set the crime's solved property
                mCrime.setSolved(isChecked); //If is checked = true, solved = true and vice versa
            }
        });

        return v;
    }

    //Add newInstance method to maintain encapsulation of fragment.
    //There is no communication with the hosting activity here
    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle(); //Create Bundle
        args.putSerializable(EXTRA_CRIME_ID, crimeId); //Put away arguments

        CrimeFragment fragment = new CrimeFragment(); //Create fragment

        fragment.setArguments(args); //set arguments
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if(requestCode == REQUEST_DATE){
            MutableDateTime date = (MutableDateTime)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.getDate().setYear(date.getYear());
            mCrime.getDate().setMonthOfYear(date.getMonthOfYear());
            mCrime.getDate().setDayOfMonth(date.getDayOfMonth());
            updateDateTime();
        }

        if(requestCode == REQUEST_TIME){
            MutableDateTime date = (MutableDateTime)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.getDate().setHourOfDay(date.getHourOfDay());
            mCrime.getDate().setMinuteOfHour(date.getMinuteOfHour());
            updateDateTime();
        }

        //This will be fired after the first dialog is closed
        if(requestCode == REQUEST_CHOICE){
            Integer choice = data.getIntExtra(ChooseDialogFragment.EXTRA_CHOICE,0);
            if(choice == CHOICE_DATE){
                //Start DatePickerFragment
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment().newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
            else if(choice == CHOICE_TIME){
                //Start TimePickerFragment
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = new TimePickerFragment().newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
            }
        }
    }
}
