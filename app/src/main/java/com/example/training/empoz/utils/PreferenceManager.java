package com.example.training.empoz.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    public static void putToPreference(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getFromPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }


}
