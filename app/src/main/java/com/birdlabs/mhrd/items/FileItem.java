package com.birdlabs.mhrd.items;

import com.birdlabs.mhrd.util.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by bijoy on 12/17/15.
 */
public class FileItem implements Serializable {
    public String file;
    public String mimeType;

    public FileItem(String file, String mimeType) {
        this.file = file;
        this.mimeType = mimeType;
    }

    public FileItem(JSONObject json) throws JSONException {
        file = json.getString("file");
        mimeType = json.getString("mime_type");
    }

    public String getLink() {
        if (file.length() > 4 && file.substring(0,4).contentEquals("http")) {
            return file;
        }
        return Api.getSlashLessBaseLink() + file;
    }
}
