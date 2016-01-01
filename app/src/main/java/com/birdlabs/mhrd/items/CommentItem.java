package com.birdlabs.mhrd.items;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by bijoy on 12/16/15.
 */
public class CommentItem implements Serializable {
    public Integer id;
    public String content;
    public String timestamp;
    public UserItem author;

    public CommentItem(JSONObject json) throws JSONException {
        id = json.getInt("id");
        content = json.getString("content");
        timestamp = json.getString("created");
        author = new UserItem(json.getJSONObject("user"));
    }
}
