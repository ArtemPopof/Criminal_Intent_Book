package com.brizzgames.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by artem96 on 25.09.16.
 */

public class CrimePagerActivity extends FragmentActivity {

    private ViewPager viewPager;
    private ArrayList<Crime> crimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);

        crimes = CrimeLab.getInstance(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

            @Override
            public int getCount() {

                return crimes.size();

            }

            @Override
            public Fragment getItem(int pos) {

                Crime crime = crimes.get(pos);
                return CriminalFragment.newInstance(crime.getId());

            }
        });

        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(CriminalFragment.EXTRA_CRIME_ID);
        for (int i = 0; i < crimes.size(); i++) {

            if (crimes.get(i).getId().equals(crimeId)) {
                viewPager.setCurrentItem(i);
                break;
            }

        }

    }

}
