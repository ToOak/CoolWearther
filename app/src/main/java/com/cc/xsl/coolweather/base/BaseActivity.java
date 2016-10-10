package com.cc.xsl.coolweather.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.cc.xsl.coolweather.BaseApplication;
import com.cc.xsl.coolweather.util.LogUtil;


/**
 * Created by xushuailong on 2016/10/10.
 */
public class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("now in " + getClass().getSimpleName().toString());
        BaseApplication.getApp().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getApp().removeActivity(this);
    }
}