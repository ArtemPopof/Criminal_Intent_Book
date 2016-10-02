package com.brizzgames.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import java.text.DateFormat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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

    private static final String TAG = "CrimeFragment";

    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final String DIALOG_PHOTO = "photo";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_CONTACT = 3;

    private Crime crime;
    private EditText titleField;
    private Button dateButton;
    private CheckBox solvedCheckBox;
    private Button timeButton;
    private Button suspectButton;
    private ImageButton photoButton;
    private ImageView photoView;

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

        photoButton = (ImageButton) v.findViewById(R.id.crime_imageButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {

            photoButton.setEnabled(false);

        }

        photoView = (ImageView)  v.findViewById(R.id.crime_imageView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo p = crime.getPhoto();

                if (p == null)
                    return;

                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                String path = getActivity()
                        .getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.newInstance(path).show(fm, DIALOG_PHOTO);
            }
        });

        Button reportButton = (Button) v.findViewById(R.id.crime_reportButton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, getString(R.string.crime_send_report));
                startActivity(i);
            }
        });

        suspectButton = (Button) v.findViewById(R.id.crime_suspectButton);
        suspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });

        if (crime.getSuspect() != null) {
            suspectButton.setText(crime.getSuspect());
        }


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

        } else if (requestCode == REQUEST_PHOTO) {

            String filename = data
                    .getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            if (filename != null) {
                Photo p = new Photo(filename);
                crime.setPhoto(p);
                showPhoto();
            }
        } else if (requestCode == REQUEST_CONTACT) {
            Uri contactUri = data.getData();

            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME
            };

            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);

            if (c.getCount() == 0) {
                c.close();
                return;
            }

            c.moveToFirst();
            String suspect = c.getString(0);

            crime.setSuspect(suspect);
            suspectButton.setText(suspect);
            c.close();

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

            case R.id.menu_delete_crime_fragment:
                CrimeLab lab = CrimeLab.getInstance(getActivity());
                lab.deleteCrime(crime);
                getActivity().finish();

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_criminal, menu);

    }

    @Override
    public void onPause() {

        super.onPause();

        CrimeLab.getInstance(getActivity()).saveCrimes();

    }

    private void showPhoto() {
        Photo p = crime.getPhoto();
        BitmapDrawable b = null;
        if (p != null) {
            String path = getActivity()
                    .getFileStreamPath(p.getFilename()).getAbsolutePath();
            b = PictureUtils.getScaledDrawable(getActivity(), path);
        }

        photoView.setImageDrawable(b);
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(photoView);
    }

    private String getCrimeReport() {
        String solvedString = null;

        if (crime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        DateFormat format = DateFormat.getInstance();

        String dateString = format.format(crime.getDate());

        String suspect = crime.getSuspect();

        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect);
        }

        String report = getString(R.string.crime_report, crime.getTitle(),
                dateString, solvedString, suspect);

        return report;

    }
}
