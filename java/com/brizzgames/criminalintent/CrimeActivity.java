package com.brizzgames.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {

        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(CriminalFragment.EXTRA_CRIME_ID);

        return CriminalFragment.newInstance(crimeId);

    }
}
