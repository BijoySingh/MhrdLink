package com.birdlabs.mhrd.util;

import com.android.volley.Request;

/**
 * The access item
 * Created by bijoy on 10/24/15.
 */
public class AccessItem {

    public static final Integer LOGIN = 0;
    public static final Integer REGISTER = 1;
    public static final Integer RESEND = 2;
    public static final Integer SIGNUP = 3;

    public static final Integer COLLEGE_LIST = 4;
    public static final Integer COLLEGE_DETAILS = 5;

    public static final Integer UPDATE_PROFILE = 6;
    public static final Integer UPDATE_PICTURE = 7;
    public static final Integer DESIGNATION = 8;
    public static final Integer ADD_DESIGNATION = 9;
    public static final Integer USER_SPECIFIC = 10;
    public static final Integer CURRENT_USER = 11;
    public static final Integer USER = 12;
    public static final Integer TAGS = 13;

    public static final Integer ADD_POST = 14;
    public static final Integer GET_POST = 15;
    public static final Integer GET_COMMENTS = 16;
    public static final Integer DELETE_POST = 17;

    public static final Integer ADD_COMMENT = 18;
    public static final Integer UPVOTE_POST_LIST = 19;
    public static final Integer DOWNVOTE_POST_LIST = 20;
    public static final Integer REMOVE_VOTE_POST_LIST = 21;

    public static final Integer UPVOTE_POST = 22;
    public static final Integer DOWNVOTE_POST = 23;
    public static final Integer REMOVE_VOTE_POST = 24;

    public String url;
    public String filename;
    public Integer type;
    public Boolean authenticated;
    public Integer method = Request.Method.POST;

    public AccessItem(String url, String filename, Integer type, Boolean authenticated) {
        this.url = url;
        this.filename = filename;
        this.type = type;
        this.authenticated = authenticated;
    }

    public AccessItem setMethod(Integer method) {
        this.method = method;
        return this;
    }
}
