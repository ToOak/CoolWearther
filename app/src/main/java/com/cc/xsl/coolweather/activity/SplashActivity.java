package com.cc.xsl.coolweather.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by xushuailong on 2016/10/10.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoChoseArea();
            }
        }, 2000);
    }

    private void gotoChoseArea() {
//        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//        startActivity(intent);
//        MainActivity.actionStart(this,"welcom",new Date().toLocaleString());
        MainActivity.actionStart(this, "welcom", DateFormat.getDateTimeInstance().format(new Date()));
        overridePendingTransition(R.anim.in_action, R.anim.out_action);
        finish();
    }
}