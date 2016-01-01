package com.birdlabs.mhrd.items;

import java.io.Serializable;

/**
 * Created by bijoy on 12/16/15.
 */
public class AuthorItem implements Serializable {
    public String name;
    public String profilePicture;

    public AuthorItem(String name, String profilePicture) {
        this.name = name;
        this.profilePicture = profilePicture;
    }

}
