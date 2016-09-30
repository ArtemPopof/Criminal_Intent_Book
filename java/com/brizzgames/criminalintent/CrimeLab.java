package com.brizzgames.criminalintent;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by artem96 on 24.09.16.
 */

public class CrimeLab {

    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private ArrayList<Crime> crimes;
    private CriminalIntentJSONSerializer serializer;

    private static CrimeLab instance;
    private Context appContext;

    private CrimeLab(Context appContext) {

        this.appContext = appContext;

        serializer = new CriminalIntentJSONSerializer(appContext, FILENAME);

        try {

            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                crimes = serializer.loadExternalCrimes();
            else
                crimes = serializer.loadCrimes();
        } catch (Exception e) {

            crimes = new ArrayList<Crime>();
            Log.e(TAG, "Error loading crimes: ", e);

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

    public void addCrime(Crime c) {

        crimes.add(c);

    }

    public boolean saveCrimes() {

        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                serializer.saveExternalCrimes(crimes);
            else
                serializer.saveCrimes(crimes);

            Log.d(TAG, "crimes saved to file");

            return true;

        } catch (Exception e) {

            Log.e(TAG, "Error saving crimes: ", e);
            return false;

        }

    }

}
