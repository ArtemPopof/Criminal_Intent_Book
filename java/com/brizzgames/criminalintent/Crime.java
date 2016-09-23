package com.brizzgames.criminalintent;

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

    public Crime() {
        // Generate unique id
        id = UUID.randomUUID();
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

}
