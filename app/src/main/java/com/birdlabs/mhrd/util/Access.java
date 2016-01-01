package com.birdlabs.mhrd.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.birdlabs.mhrd.ContentActivity;
import com.birdlabs.mhrd.MainActivity;
import com.birdlabs.mhrd.fragments.CommentsFragment;
import com.birdlabs.mhrd.fragments.ContentFragment;
import com.birdlabs.mhrd.fragments.InstitutesFragment;
import com.birdlabs.mhrd.fragments.NewsFeedFragment;
import com.birdlabs.mhrd.items.CollegeItem;
import com.birdlabs.mhrd.items.CommentItem;
import com.birdlabs.mhrd.items.NewsFeedItem;
import com.birdlabs.mhrd.items.UserItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The network call handler
 * Created by bijoy on 10/24/15.
 */
public class Access {

    public static final Integer TIMEOUT = 7500;

    public static final String SUCCESS = "success";
    public static final String TOKEN = "token";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";

    Context context;

    private Access(Context context) {
        this.context = context;
    }

    public static Access getInstance(Context context) {
        return new Access(context);
    }

    public void getData(final AccessItem access, final Fragment fragment) {
        Preferences preferences = Preferences.getInstance(context);

        if (!preferences.isLoggedIn()) {
            return;
        }

        StringRequest jsonRequest = new StringRequest(
                Request.Method.GET, access.url, new Response
                .Listener<String>() {
            @Override
            public void onResponse(String json) {
                Log.d(Access.class.getSimpleName(), json);

                try {
                    if (access.type.equals(AccessItem.COLLEGE_LIST)) {
                        Functions.offlineDataWriter(context, access.filename, json);
                        ((InstitutesFragment) fragment).refreshList();
                    } else if (access.type.equals(AccessItem.COLLEGE_DETAILS)) {
                        JSONObject object = new JSONObject(json);
                        CollegeItem item = new CollegeItem(object);
                    } else if (access.type.equals(AccessItem.CURRENT_USER)) {
                        JSONObject object = new JSONObject(json);
                        UserItem item = new UserItem(object);
                        Preferences preferences = Preferences.getInstance(context);
                        Functions.offlineDataWriter(context, access.filename, json);
                        preferences.saveUserId(item.id);
                        preferences.saveFirstName(item.first_name);
                        preferences.saveLastName(item.last_name);
                        preferences.saveEmail(item.email);
                        preferences.saveCollegeId(item.college.id);
                        preferences.saveUserLogo(item.picture.file);
                    } else if (access.type.equals(AccessItem.TAGS)) {
                        Functions.offlineDataWriter(context, access.filename, json);
                    } else if (access.type.equals(AccessItem.GET_POST)) {
                        Functions.offlineDataWriter(context, access.filename, json);
                        ((NewsFeedFragment) fragment).refreshList();
                    } else if (access.type.equals(AccessItem.GET_COMMENTS)) {
                        Functions.offlineDataWriter(context, access.filename, json);
                        ((CommentsFragment) fragment).refreshList();
                    }
                } catch (JSONException e) {
                    Log.e(Access.class.getSimpleName(), e.getMessage(), e);
                } catch (Exception e) {
                    Log.e(Access.class.getSimpleName(), e.getMessage(), e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String string = new String(error.networkResponse.data);
                    Log.e(Access.class.getSimpleName(), string);
                } catch (Exception e) {
                    error.printStackTrace();
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (access.authenticated) {
                    params.put("token-auth", Preferences.getInstance(context).getToken());
                }
                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public void sendData(final AccessItem access,
                         final Fragment fragment,
                         final Map<String, Object> map) {
        Preferences preferences = Preferences.getInstance(context);

        Log.d(Access.class.getSimpleName(), new JSONObject(map).toString());
        Log.d(Access.class.getSimpleName(), access.url);


        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                access.method,
                access.url,
                new JSONObject(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d(Access.class.getSimpleName(), json.toString());

                        try {
                            if (access.type.equals(AccessItem.LOGIN) || access.type.equals(AccessItem.SIGNUP)) {
                                if (json.getBoolean(SUCCESS)) {
                                    Preferences.getInstance(context).saveToken(json.getString(TOKEN));
                                    Preferences.getInstance(context).login();

                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);
                                    ((Activity) context).finish();
                                }
                            } else if (access.type.equals(AccessItem.REGISTER)
                                    || access.type.equals(AccessItem.RESEND)) {
                                if (json.getBoolean(SUCCESS)) {
                                    Functions.makeToast(context, json.getString(MESSAGE));
                                }
                            } else if (access.type.equals(AccessItem.UPDATE_PICTURE)) {
                                Functions.makeToast(context, "Picture Updated");
                            } else if (access.type.equals(AccessItem.UPDATE_PROFILE)) {
                                Functions.makeToast(context, "Profile Updated");
                            } else if (access.type.equals(AccessItem.ADD_POST)) {
                                Functions.makeToast(context, "Post Added");
                                NewsFeedItem item = new NewsFeedItem(json);
                                Intent intent = new Intent(context, ContentActivity.class);
                                intent.putExtra(ContentActivity.NEWS_FEED_ITEM, item);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            } else if (access.type.equals(AccessItem.DELETE_POST)) {
                                if (json.getBoolean(SUCCESS)) {
                                    Functions.makeToast(context, json.getString(MESSAGE));
                                }
                            } else if (access.type.equals(AccessItem.ADD_COMMENT)) {
                                Functions.makeToast(context, "Comment Added");
                                ((CommentsFragment) fragment).addComment(new CommentItem(json));
                                ((CommentsFragment) fragment).clearNewComment();
                                ((CommentsFragment) fragment).requestData();
                            } else if (access.type.equals(AccessItem.UPVOTE_POST_LIST)) {
                                Functions.makeToast(context, "Upvoted");
                                ((NewsFeedFragment) fragment).addNewsFeed(new NewsFeedItem(json));
                            } else if (access.type.equals(AccessItem.DOWNVOTE_POST_LIST)) {
                                Functions.makeToast(context, "Downvoted");
                                ((NewsFeedFragment) fragment).addNewsFeed(new NewsFeedItem(json));
                            } else if (access.type.equals(AccessItem.REMOVE_VOTE_POST_LIST)) {
                                Functions.makeToast(context, "Vote Removed");
                                ((NewsFeedFragment) fragment).addNewsFeed(new NewsFeedItem(json));
                            } else if (access.type.equals(AccessItem.UPVOTE_POST)) {
                                Functions.makeToast(context, "Upvoted");
                                ((ContentFragment) fragment).addNewsFeed(new NewsFeedItem(json));
                            } else if (access.type.equals(AccessItem.DOWNVOTE_POST)) {
                                Functions.makeToast(context, "Downvoted");
                                ((ContentFragment) fragment).addNewsFeed(new NewsFeedItem(json));
                            } else if (access.type.equals(AccessItem.REMOVE_VOTE_POST)) {
                                Functions.makeToast(context, "Vote Removed");
                                ((ContentFragment) fragment).addNewsFeed(new NewsFeedItem(json));
                            }
                        } catch (Exception e) {
                            Log.e(Access.class.getSimpleName(), e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    String string = new String(error.networkResponse.data);
                    Log.e(Access.class.getSimpleName(), string);
                    Functions.makeToast(context, new JSONObject(string).getString(MESSAGE));
                } catch (Exception e) {
                    error.printStackTrace();
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (access.authenticated) {
                    params.put("token-auth", Preferences.getInstance(context).getToken());
                }
                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonRequest);
    }

}
