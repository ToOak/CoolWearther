package com.cc.xsl.coolweather.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cc.xsl.coolweather.MyApplication;

public class ScreenUtil {
    private static ScreenUtil screenUtil = null;
    private Context context;
    private int width = 0;
    private int height = 0;
    private float density = 0;
    private DisplayMetrics metrics = new DisplayMetrics();

    private ScreenUtil(Context context) {
        this.context = context;
    }

    public int getScreenWidth() {
        if (width == 0) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                manager.getDefaultDisplay().getMetrics(metrics);
            }
            width = metrics.widthPixels;
        }
        return width;
    }

    public int getScreenWidth2() {
        DisplayMetrics dm = new DisplayMetrics();
        MyApplication.getApp().getTaskTopActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public int getScreenHeight() {
        if (height == 0) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                manager.getDefaultDisplay().getMetrics(metrics);
            }
            height = metrics.heightPixels;
        }
        return height;
    }

    public int dip2px(float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int getScreenStateHeight() {
        int stateBarheight = 0;
        // 获取status_bar_height资源id
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            // 根据资源id获取相应的尺寸值
            stateBarheight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return stateBarheight;
    }


    public void showInputMethod(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    public void hideInputMethod(EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏键盘
//            imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        editText.clearFocus();
    }

    public static ScreenUtil getInstance(Context context) {
        if (screenUtil == null) {
            synchronized (ScreenUtil.class) {
                if (screenUtil == null) {
                    screenUtil = new ScreenUtil(context);
                }
            }
        }
        return screenUtil;
    }
}
