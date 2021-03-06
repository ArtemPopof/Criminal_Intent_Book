package com.brizzgames.criminalintent;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by artem96 on 29.09.16.
 */

public class CriminalIntentJSONSerializer {

    private Context context;
    private String fileName;

    public CriminalIntentJSONSerializer(Context c, String f) {

        context = c;
        fileName = f;

    }

    public void saveCrimes(ArrayList<Crime> crimes)
        throws JSONException, IOException {

        JSONArray array = new JSONArray();

        for (Crime c : crimes)
            array.put(c.toJSON());

        Writer writer = null;

        try {

            OutputStream out = context
                    .openFileOutput(fileName, Context.MODE_PRIVATE);

            writer = new OutputStreamWriter(out);
            writer.write(array.toString());

        } finally {

            if (writer != null)
                writer.close();

        }

    }

    public void saveExternalCrimes(ArrayList<Crime> crimes)
        throws JSONException, IOException {

        JSONArray array = new JSONArray();
        Writer writer = null;

        File crimesFile = new File(context.getExternalFilesDir(null), fileName);

        for (Crime c : crimes)
            array.put(c.toJSON());

        try {

            writer = new BufferedWriter(new FileWriter(crimesFile));

            writer.write(array.toString());

        } finally {

            if (writer != null)
                writer.close();

        }

    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {

        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;

        try {

            InputStream in = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();

            for (int i = 0; i < array.length(); i++) {

                crimes.add(new Crime(array.getJSONObject(i)));

            }

        } catch (FileNotFoundException e) {

        } finally {

            if (reader != null)
                reader.close();

        }

        return crimes;
    }

    public ArrayList<Crime> loadExternalCrimes()
            throws IOException, JSONException {

        ArrayList<Crime> crimes = new ArrayList<Crime>();

        File crimesDir = context.getExternalFilesDir(null);
        File crimesFile = new File(crimesDir, fileName);

        BufferedReader reader = null;
        String string = "";
        StringBuilder jsonString = new StringBuilder();

        try {

            FileInputStream fis = new FileInputStream(crimesFile);
            reader = new BufferedReader(new InputStreamReader(fis));


            while ((string = reader.readLine()) != null) {

                jsonString.append(string);

            }

            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();

            for (int i = 0; i < array.length(); i++) {

                crimes.add(new Crime(array.getJSONObject(i)));

            }
        } catch (FileNotFoundException e) {

            if (reader != null)
                reader.close();

            Log.e("CriminalIntent: ",
                    "Crimes file not found. File path: "+crimesFile.getAbsolutePath());

        }

        return crimes;

    }

}
