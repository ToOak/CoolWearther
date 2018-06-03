package com.cc.xsl.coolweather.util;

import android.util.Log;

import com.cc.xsl.coolweather.BaseApplication;

/**
 * Created by xushuailong on 2016/10/10.
 */
public class LogUtil {

    private static String FLAG = "oak";

    public static void v(String msg) {
        if (BaseApplication.getApp().isDebug) {
            Log.v(FLAG, msg);
        }
    }

    public static void i(String msg) {
        if (BaseApplication.getApp().isDebug) {
            Log.i(FLAG, msg);
        }
    }

    public static void d(String msg) {
        if (BaseApplication.getApp().isDebug) {
            Log.d(FLAG, msg);
        }
    }

    public static void e(String msg) {
        if (BaseApplication.getApp().isDebug) {
            Log.e(FLAG, msg);
        }
    }

    public static void w(String msg) {
        if (BaseApplication.getApp().isDebug) {
            Log.w(FLAG, msg);
        }
    }
}
