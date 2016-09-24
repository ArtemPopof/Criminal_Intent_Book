package com.brizzgames.criminalintent;

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

    private UUID id;
    private String title;
    private Date date;
    private boolean solved;

    public Crime() {
        // Generate unique id
        id = UUID.randomUUID();

        date = new Date();
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

    @Override
    public String toString() {
        return title;
    }

}
