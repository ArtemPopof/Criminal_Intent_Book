package com.brizzgames.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Controller class for Crime model
 *
 * Manage single crime record
 *
 * Created by artem96 on 24.09.16.
 */

public class CriminalFragment extends Fragment {

    private Crime crime;
    private EditText titleField;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {

        super.onCreate(onSavedInstanceState);
        crime = new Crime();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        View  v = inflater.inflate(R.layout.fragment_crime, parent, false);

        titleField = (EditText) v.findViewById(R.id.crime_title);
        titleField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {

                crime.setTitle(c.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {

                // empty space for further development

            }

            public void afterTextChanged(Editable c) {

                // too

            }

        });


        return v;

    }
}
