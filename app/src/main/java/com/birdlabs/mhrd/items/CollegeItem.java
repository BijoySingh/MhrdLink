package com.birdlabs.mhrd.items;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * news feed item
 * Created by bijoy on 12/16/15.
 */
public class CollegeItem implements Serializable {
    public Integer id;
    public FileItem logo;
    public FileItem cover;
    public String name;
    public String location;
    public String phone;
    public String homepage;

    public CollegeItem(Integer id, FileItem logo, FileItem cover, String name, String location, String phone, String homepage) {
        this.id = id;
        this.logo = logo;
        this.cover = cover;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.homepage = homepage;
    }

    public CollegeItem(JSONObject json) throws JSONException {
        id = json.getInt("id");
        logo = new FileItem(json.getJSONObject("logo"));
        cover = new FileItem(json.getJSONObject("cover"));
        name = json.getString("name");
        location = json.getString("location");
        phone = json.getString("phone");
        homepage = json.getString("homepage");
    }
}
