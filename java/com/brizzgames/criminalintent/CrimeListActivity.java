package com.brizzgames.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.Window;
import android.widget.Toolbar;

/**
 * Created by artem96 on 24.09.16.
 */

public class CrimeListActivity extends SingleFragmentActivity {


    @Override
    public void onCreate(Bundle onCreateSavedInstance) {
        super.onCreate(onCreateSavedInstance);

    }

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }



}
