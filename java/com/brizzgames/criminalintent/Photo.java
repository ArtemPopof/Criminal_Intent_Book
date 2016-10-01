package com.brizzgames.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by artem96 on 01.10.16.
 */

public class Photo {
    private static final String JSON_FILENAME = "filename";

    private String fileName;

    public Photo(String fileName) {
        this.fileName = fileName;
    }

    public Photo(JSONObject json) throws JSONException {
        fileName = json.getString(JSON_FILENAME);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME, fileName);
        return json;
    }

    public String getFilename() {
        return fileName;
    }
}
