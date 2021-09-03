package com.pankajranag.rana_gaming;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefrenceHelper {

    Context curr_context;

    public static final String USER_CONTACT = "user_contact";

    public void addString(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(curr_context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        String value = null;
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(curr_context);
        value = sp.getString(key, null);
        return value;
    }
}
