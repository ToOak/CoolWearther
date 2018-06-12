package com.cc.xsl.coolweather.util;

import android.annotation.SuppressLint;
import android.util.Log;


/**
 * Created by xushuailong on 2016/10/10.
 */
public class LogUtil {

    private static String FLAG = "oak";

    @SuppressLint("DefaultLocale")
    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = FLAG + ":" + tag;
        return tag;
    }


    public static void v(String msg) {
        if (Config.IS_DEBUG) {
            Log.v(generateTag(), msg);
        }
    }

    public static void i(String msg) {
        if (Config.IS_DEBUG) {
            Log.i(generateTag(), msg);
        }
    }

    public static void d(String msg) {
        if (Config.IS_DEBUG) {
            Log.d(generateTag(), msg);
        }
    }

    public static void e(String msg) {
        if (Config.IS_DEBUG) {
            Log.e(generateTag(), msg);
        }
    }

    public static void w(String msg) {
        if (Config.IS_DEBUG) {
            Log.w(generateTag(), msg);
        }
    }
}
