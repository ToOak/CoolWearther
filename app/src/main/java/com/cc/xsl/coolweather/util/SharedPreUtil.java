package com.cc.xsl.coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.cc.xsl.coolweather.MyApplication;

public class SharedPreUtil {
    private final String preferenceName = "ccPreferences";
    public static final String NO_VALUE = "no_value";
    private SharedPreferences preferences;

    private SharedPreUtil() {
        preferences = MyApplication.getApp().getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public SharedPreUtil put(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
        return this;
    }

    public String getString(String key) {
        return preferences.getString(key, NO_VALUE);
    }

    public SharedPreUtil put(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
        return this;
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    private static SharedPreUtil sharedPreUtil = null;

    public static SharedPreUtil getInstance() {
        if (sharedPreUtil == null) {
            synchronized (SharedPreUtil.class) {
                if (sharedPreUtil == null) {
                    sharedPreUtil = new SharedPreUtil();
                }
            }
        }
        return sharedPreUtil;
    }

}
