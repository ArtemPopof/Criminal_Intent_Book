package com.brizzgames.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 *  Crime is part of CrimeActivity model level.
 *  It represents office crime with unique id and
 *  title.
 *
 *  Example: Trash on the floor.
 *
 * Created by Artem Popov on 23.09.16.
 */

public class Crime {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";

    private UUID id;
    private String title;
    private Date date;
    private boolean solved;

    public Crime() {
        // Generate unique id
        id = UUID.randomUUID();

        date = new Date();
    }

    public Crime(JSONObject json) throws JSONException {

        id = UUID.fromString(json.getString(JSON_ID));
        title = json.getString(JSON_TITLE);
        solved = json.getBoolean(JSON_SOLVED);
        date = new Date(json.getString(JSON_DATE));

    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public String getTitle() {
        return title;
    }

    public UUID getId() {
        return id;
    }

    public void setDate(Date newDate) {
        date = newDate;
    }

    public Date getDate() {
        return date;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(JSON_ID, id.toString());
        json.put(JSON_TITLE, title);
        json.put(JSON_SOLVED, solved);
        json.put(JSON_DATE, date.toString());

        return json;

    }

    @Override
    public String toString() {
        return title;
    }

}
