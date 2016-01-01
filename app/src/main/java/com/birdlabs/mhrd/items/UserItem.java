package com.birdlabs.mhrd.items;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by bijoy on 12/18/15.
 */
public class UserItem implements Serializable {
    public Integer id;
    public String first_name;
    public String last_name;
    public String email;
    public CollegeItem college;
    public FileItem picture;

    public UserItem(JSONObject json) throws JSONException {
        id = json.getInt("id");
        first_name = json.getString("first_name");
        last_name = json.getString("last_name");
        email = json.getString("email");

        try {
            picture = new FileItem(json.getJSONObject("picture"));
            college = new CollegeItem(json.getJSONObject("college"));
        } catch (Exception e) {
            ;
        }

    }

    public String getName() {
        return first_name + " " + last_name;
    }
}
