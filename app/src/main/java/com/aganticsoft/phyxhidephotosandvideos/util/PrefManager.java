package com.aganticsoft.phyxhidephotosandvideos.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ttson
 * Date: 9/24/2017.
 */

public class PrefManager {
    private static PrefManager instance;

    private Context applicationContext;
    private SharedPreferences pref;

    public static final String MAIN_PREF = "main.pref";


    private PrefManager(Context context) {
        this.applicationContext = context;

        pref = applicationContext.getSharedPreferences(MAIN_PREF, Context.MODE_PRIVATE);
    }

    /**
     * Use application context only
     * @param context application context
     * @return instance
     */
    public static PrefManager getInstance(Context context) {
        if (instance == null)
            instance = new PrefManager(context.getApplicationContext());

        return instance;
    }

    public void putBoolean(String key, boolean value) {
        pref.edit()
                .putBoolean(key, value)
                .apply();
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    public String getString(String key) {
        return pref.getString(key, "");
    }

    public void putString(String key, String value) {
        pref.edit()
                .putString(key, value)
                .apply();
    }

    public int getInt(String key)  {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public void putInt(String key, int value) {
        pref.edit()
                .putInt(key, value)
                .apply();
    }
}
