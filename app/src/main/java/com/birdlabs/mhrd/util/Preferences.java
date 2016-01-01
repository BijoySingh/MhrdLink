package com.birdlabs.mhrd.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Stores and loads from the shared preferences
 * Created by bijoy on 10/9/15.
 */
public class Preferences {

    Context context;

    public static final String SHARED_PREFERENCES = "MHRD";

    /**
     * Shared Preference Keys
     */
    public static final String KEY_LOGIN = "LOGIN";
    public static final String KEY_FIRST_NAME = "FIRST_NAME";
    public static final String KEY_LAST_NAME = "LAST_NAME";
    public static final String KEY_USERNAME = "USERNAME";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_PASSWORD = "PASSWORD";
    public static final String KEY_TOKEN = "TOKEN";
    public static final String KEY_COLLEGE_ID = "COLLEGE_ID";
    public static final String KEY_USER_ID = "USER_ID";
    public static final String KEY_USER_LOGO = "KEY_USER_LOGO";

    public static final String TRUE = "true";
    public static final String FALSE = "false";


    /**
     * Load the data from the shared preferences
     *
     * @param key the key of the data
     * @return the value stored or a default
     */
    public String load(String key) {
        SharedPreferences sp = context.getSharedPreferences(
                SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * Saves the data into the shared preferences
     *
     * @param key   the key of the data
     * @param value the value to store
     */
    public void save(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(
                SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Saves a boolean variable
     *
     * @param key  the key
     * @param bool the bool to store
     */
    public void saveBoolean(String key, Boolean bool) {
        if (bool) {
            save(key, TRUE);
        } else {
            save(key, FALSE);
        }
    }

    /**
     * Gets the stored boolean value
     *
     * @param key         the key
     * @param defaultBool boolean if it wasnt stored before or is not a bool
     * @return the stored value as boolean
     */
    public Boolean loadBoolean(String key, Boolean defaultBool) {
        String stored = load(key);
        if (stored.contentEquals(TRUE)) {
            return true;
        } else if (stored.contentEquals(FALSE)) {
            return false;
        } else {
            return defaultBool;
        }
    }

    /**
     * Private Constructor
     *
     * @param context activity context
     */
    private Preferences(Context context) {
        this.context = context;
    }

    /**
     * Factory method to give the preferences
     *
     * @param context activity context
     * @return the preference object
     */
    public static Preferences getInstance(Context context) {
        return new Preferences(context);
    }

    public void reset(String key) {
        save(key, "");
    }

    public void saveFirstName(String value) {
        save(KEY_FIRST_NAME, value);
    }

    public void saveLastName(String value) {
        save(KEY_LAST_NAME, value);
    }

    public void saveUsername(String value) {
        save(KEY_USERNAME, value);
    }

    public void savePassword(String value) {
        save(KEY_PASSWORD, value);
    }

    public void saveToken(String value) {
        save(KEY_TOKEN, value);
    }

    public void saveEmail(String value) {
        save(KEY_EMAIL, value);
    }

    public void saveUserLogo(String value) {
        save(KEY_USER_LOGO, value);
    }

    public void saveUserId(Integer value) {
        save(KEY_USER_ID, "" + value);
    }

    public void saveCollegeId(Integer value) {
        save(KEY_COLLEGE_ID, "" + value);
    }

    public Integer getInteger(String key, Integer defaultValue) {
        String value = load(key);
        if (value.isEmpty()) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }

    public String getToken() {
        return load(KEY_TOKEN);
    }

    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    public String getFirstName() {
        return load(KEY_FIRST_NAME);
    }

    public String getLastName() {
        return load(KEY_LAST_NAME);
    }

    public String getEmail() {
        return load(KEY_EMAIL);
    }

    public String getUserLogo() {
        return load(KEY_USER_LOGO);
    }

    public Integer getUserId() {
        return getInteger(KEY_USER_ID, 0);
    }

    public String getPassword() {
        return load(KEY_PASSWORD);
    }

    public String getUsername() {
        return load(KEY_USERNAME);
    }

    public Integer getCollegeId() {
        return getInteger(KEY_COLLEGE_ID, 0);
    }

    public void login() {
        saveBoolean(KEY_LOGIN, true);
    }

    public void logout() {
        saveBoolean(KEY_LOGIN, false);
    }

    public Boolean isLoggedIn() {
        return loadBoolean(KEY_LOGIN, false);
    }

}
