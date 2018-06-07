package com.cc.xsl.coolweather.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cc.xsl.coolweather.BaseApplication;
import com.cc.xsl.coolweather.util.LogUtil;


/**
 * Created by xushuailong on 2016/10/10.
 */
@SuppressLint("Registered")
public class BaseActivity extends Activity {
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("now in " + getClass().getSimpleName());
        BaseApplication.getApp().addActivity(this);
        context = BaseApplication.getApp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getApp().removeActivity(this);
    }

    /**
     * 退出方法
     */
    public void hideApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
