package com.brizzgames.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import java.text.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Controller class for Crime model
 *
 * Manage single crime record
 *
 * Created by artem96 on 24.09.16.
 */

public class CriminalFragment extends Fragment {

    public static final String EXTRA_CRIME_ID =
            "com.brezzegames.criminalintent.crime_id";

    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Crime crime;
    private EditText titleField;
    private Button dateButton;
    private CheckBox solvedCheckBox;
    private Button timeButton;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {

        super.onCreate(onSavedInstanceState);

        UUID crimeId = (UUID) getArguments().getSerializable(CriminalFragment.EXTRA_CRIME_ID);

        crime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);

        setHasOptionsMenu(true);

    }

    @TargetApi(11)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        View  v = inflater.inflate(R.layout.fragment_crime, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            if (NavUtils.getParentActivityName(getActivity()) != null) {

            }

        }

        titleField = (EditText) v.findViewById(R.id.crime_title);
        titleField.setText(crime.getTitle());
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

        dateButton = (Button) v.findViewById(R.id.crime_date);

        updateDate();
        dateButton.setText(crime.getDate().toString());
        dateButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();

                DatePickerFragment dialog = DatePickerFragment.newInstance(crime.getDate());
                dialog.setTargetFragment(CriminalFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);

            }
        });

        timeButton = (Button) v.findViewById(R.id.crime_time_button);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(crime.getDate());
        timeButton.setText(calendar.get(Calendar.HOUR_OF_DAY ) + ":" + calendar.get(Calendar.MINUTE));
        timeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(crime.getDate());
                TimePickerFragment dialog = TimePickerFragment.createInstance(calendar);
                dialog.setTargetFragment(CriminalFragment.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
            }

        });
        solvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        solvedCheckBox.setChecked(crime.isSolved());
        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // set crime solved flag
                crime.setSolved(isChecked);

            }

        });



        return v;

    }

    public static CriminalFragment newInstance(UUID crimeId) {

        Bundle arguments = new Bundle();
        arguments.putSerializable(EXTRA_CRIME_ID, crimeId);
        CriminalFragment fragment = new CriminalFragment();
        fragment.setArguments(arguments);

        return fragment;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {

            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setDate(date);
            dateButton.setText(crime.getDate().toString());
            updateDate();
        } else if (requestCode == REQUEST_TIME) {

            Calendar currentTime = (Calendar)
                    data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);

            crime.setDate(currentTime.getTime());
            timeButton.setText(currentTime.get(Calendar.HOUR_OF_DAY )
                    + ":" + currentTime.get(Calendar.MINUTE));

        }

    }

    private void updateDate() {

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String newDateFormatString = crime.getDate().toString().split(" ")[0] + ", ";
        newDateFormatString += df.format(crime.getDate());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                if (NavUtils.getParentActivityName(getActivity()) != null) {

                    NavUtils.navigateUpFromSameTask(getActivity());
                }

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onPause() {

        super.onPause();

        CrimeLab.getInstance(getActivity()).saveCrimes();

    }
}
