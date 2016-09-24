package com.brizzgames.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by artem96 on 24.09.16.
 */

public class CrimeLab {

    private ArrayList<Crime> crimes;

    private static CrimeLab instance;
    private Context appContext;

    private CrimeLab(Context appContext) {

        this.appContext = appContext;
        crimes = new ArrayList<Crime>();

        for (int i = 0; i < 100; i++) {

            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0);
            crimes.add(c);

        }

    }

    public static CrimeLab getInstance(Context c) {

        if (instance == null) {

            instance = new CrimeLab(c.getApplicationContext());

        }

        return instance;

    }

    public ArrayList<Crime> getCrimes() {

        return crimes;

    }

    public Crime getCrime(UUID id) {

        for (Crime c : crimes) {

            if (c.getId().equals(id)) {

                return c;

            }

        }

        return null;
    }

}
