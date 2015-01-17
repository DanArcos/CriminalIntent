package com.cornbread.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

//Notice this fragment class takes from the support library!
public class CrimeFragment extends Fragment{
    private Crime mCrime; //Crime object
    private EditText mTitleField;

    //This is a public function whereas Activity.onCreate is a protected
    //This must be public so that the activity (a different) can call it.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    //This function inflates the view
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
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

        return v;
    }
}
