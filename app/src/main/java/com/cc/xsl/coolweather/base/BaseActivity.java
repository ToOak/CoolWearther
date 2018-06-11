package com.cc.xsl.coolweather.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.cc.xsl.coolweather.MyApplication;
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
        MyApplication.getApp().addActivity(this);
        context = MyApplication.getApp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApp().removeActivity(this);
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
