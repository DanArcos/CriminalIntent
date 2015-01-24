package com.cornbread.android.criminalintent;

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

import java.util.UUID;

//Notice this fragment class takes from the support library!
public class CrimeFragment extends Fragment{
    public static final String EXTRA_CRIME_ID = "com.cornbread.android.criminalintent.crime_id";
    public static final String DIALOG_DATE = "date";

    private Crime mCrime; //Crime object
    private EditText mTitleField;
    private Button mDateButton;
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

        //Code for DateButton Widget
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.formatDate());
        //mDateButton.setEnabled(false); //setEnabled - Enables/Disables buttons
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(fm, DIALOG_DATE); //The Fragment Manager needs to be called to manage the dialog there
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
}
