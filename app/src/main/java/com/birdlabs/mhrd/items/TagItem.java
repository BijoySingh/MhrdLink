package com.birdlabs.mhrd.items;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bijoy on 12/18/15.
 */
public class TagItem {
    public Integer id;
    public String tag;

    public TagItem(Integer id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public TagItem(JSONObject json) throws JSONException {
        id = json.getInt("id");
        tag = json.getString("tag");
    }

    public static TagItem getDefault() {
        return new TagItem(null, "--- Add Tag ---");
    }
}
