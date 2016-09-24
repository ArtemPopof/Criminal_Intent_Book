package com.brizzgames.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by artem96 on 24.09.16.
 */

public class CrimeListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }



}
