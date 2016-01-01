package com.birdlabs.mhrd.items;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * news feed item
 * Created by bijoy on 12/16/15.
 */
public class NewsFeedItem implements Serializable {
    public Integer id;
    public String title;
    public String content;
    public String[] tags;
    public String created;
    public Integer upvote;
    public Integer downvote;
    public Integer user_vote;
    public Boolean anonymous;
    public UserItem author;

    public NewsFeedItem(JSONObject json) throws JSONException {
        Log.d(NewsFeedItem.class.getSimpleName(), json.toString());

        id = json.getInt("id");
        title = json.getString("title");
        content = json.getString("content");
        created = json.getString("created");

        anonymous = json.getBoolean("anonymous");

        upvote = json.getInt("upvotes");
        downvote = json.getInt("downvotes");

        if (!json.getString("user").contentEquals("null")) {
            author = new UserItem(json.getJSONObject("user"));
        }

        JSONArray tagsArray = json.getJSONArray("tags");
        tags = new String[tagsArray.length()];
        for (int i = 0; i < tagsArray.length(); i++) {
            tags[i] = tagsArray.getString(i);
        }

        user_vote = json.getInt("user_vote");
    }
}
