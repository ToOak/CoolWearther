package com.cc.xsl.coolweather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.cc.xsl.coolweather.util.ToastUtil;

import java.lang.annotation.Native;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xushuailong on 2016/10/10.
 */
public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static MyApplication app;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        context = getApplicationContext();
        ToastUtil.showErrorMessage("JNI测试", getStringFromC());
    }

    public static Context getContext() {
        return context;
    }

    public static MyApplication getApp() {
        return app;
    }

    public void addActivity(Activity activity) {
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public Activity getTaskTopActivity() {
        if (activities != null && activities.size() > 0) {
            return activities.get(activities.size() - 1);
        }
        return null;
    }

    public native String getStringFromC();

    static   {
        System.loadLibrary("hello");
    }
}
