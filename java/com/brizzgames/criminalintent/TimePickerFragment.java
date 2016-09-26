package com.brizzgames.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by artem96 on 25.09.16.
 */

public class TimePickerFragment extends AppCompatDialogFragment {

    public static final String EXTRA_TIME = "extra_time";

    private Calendar crimeTime;

    @Override
    public Dialog onCreateDialog(Bundle onSavedInstanceState) {

        crimeTime = (Calendar) getArguments().getSerializable(EXTRA_TIME);


        View v = (View) getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);
        timePicker.setCurrentHour(crimeTime.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(crimeTime.get(Calendar.MINUTE));


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                crimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                crimeTime.set(Calendar.MINUTE, minute);

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Time of the crime.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sendResult(Activity.RESULT_OK);

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();

    }

    public static TimePickerFragment createInstance(Calendar currentTime) {

        Bundle data = new Bundle();
        data.putSerializable(EXTRA_TIME, currentTime);

        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.setArguments(data);

        return timePicker;
    }

    private void sendResult(int resultCode) {

        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, crimeTime);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);

    }


}
