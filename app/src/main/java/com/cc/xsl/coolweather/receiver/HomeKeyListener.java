package com.cc.xsl.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 对Home键的监听
 */
public class HomeKeyListener extends BroadcastReceiver {
    private Context context;

    public HomeKeyListener(Context context) {
        this.context = context;
    }

    /**
     * 通常在activity的onStart方法中调用
     */
    public void start() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(this, filter);
    }

    /**
     * 通常在activity的onStop方法中调用
     */
    public void stop() {
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        String action = intent.getAction();
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                // 按下Home键
                if (onHomeKeyPressListener != null) {
                    onHomeKeyPressListener.onHomePress();
                }
            } else if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
                // recent apps
                if (onRecentAppsPressListener != null) {
                    onRecentAppsPressListener.onRecentAppsPress();
                }
            }
        }
    }

    private OnHomeKeyPressListener onHomeKeyPressListener;

    public void setOnHomeKeyPressListener(OnHomeKeyPressListener onHomeKeyPressListener) {
        this.onHomeKeyPressListener = onHomeKeyPressListener;
    }

    /**
     * 按下Home键 两个回调分开写可用于lambda表达式
     */
    public interface OnHomeKeyPressListener {
        void onHomePress();
    }

    private OnRecentAppsPressListener onRecentAppsPressListener;

    public void setOnRecentAppsPressListener(OnRecentAppsPressListener onRecentAppsPressListener) {
        this.onRecentAppsPressListener = onRecentAppsPressListener;
    }

    /**
     * Coolpad changer S1 是查看后台任务栈
     */
    public interface OnRecentAppsPressListener {
        void onRecentAppsPress();
    }
}
