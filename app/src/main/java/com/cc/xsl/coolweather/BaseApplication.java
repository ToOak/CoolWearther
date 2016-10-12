package com.cc.xsl.coolweather;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xushuailong on 2016/10/10.
 */
public class BaseApplication extends Application {
    public boolean isDebug = true;
    private static BaseApplication app;
    private static Context context;
    private List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
    public static BaseApplication getApp(){
        return app;
    }
    public void addActivity(Activity activity){
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
    }
    public void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public void finishAll(){
        for (Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
