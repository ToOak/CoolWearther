package com.cc.xsl.coolweather.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.receiver.HomeKeyListener;
import com.cc.xsl.coolweather.util.LogUtil;
import com.cc.xsl.coolweather.util.ToastUtil;

public class HomeDownActivity extends BaseActivity implements View.OnClickListener {
    private HomeKeyListener homeKeyListener;
    private Button goHomeBtn;

    public static Intent getAction(Context context) {
        Intent intent = new Intent(context, HomeDownActivity.class);
        return intent;
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

        homeKeyListener = new HomeKeyListener(context);
        homeKeyListener.setOnHomeKeyPressListener(new HomeKeyListener.OnHomeKeyPressListener() {
            @Override
            public void onHomePress() {
                ToastUtil.showMessage("onHomePress...");
            }
        });
        homeKeyListener.setOnRecentAppsPressListener(new HomeKeyListener.OnRecentAppsPressListener() {
            @Override
            public void onRecentAppsPress() {
                ToastUtil.showMessage("onRecentAppsPress...");
            }
        });

        goHomeBtn = (Button) findViewById(R.id.go_home_btn);
        goHomeBtn.setOnClickListener(this);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_down;
    }

    @Override
    protected void onPause() {
        LogUtil.e("onPause");
        // 按下 home键 & 返回键时 不起到作用
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            activityManager.moveTaskToFront(getTaskId(), 0);
        }
        super.onPause();
    }

    @Override
    protected void onStart() {
        homeKeyListener.start();
        LogUtil.e("onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        LogUtil.e("onStop");
        homeKeyListener.stop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        LogUtil.e("onResume");
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ToastUtil.showMessage("onKeyDown Back...");
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            ToastUtil.showMessage("onKeyDown Home...");
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_home_btn: {
                // TODO 为虾米不生效 参照覆写的onPause方法
                hideApp();
//                BaseApplication.getApp().finishAll();
                break;
            }
        }
    }
}
