package com.cc.xsl.coolweather.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cc.xsl.coolweather.MyApplication;


/**
 * Created by xushuailong on 2016/10/10.
 * 对资源文件获取
 */
public class ResUtil {
    public static int getColor(int resId) {
        return MyApplication.getApp().getResources().getColor(resId);
    }

    public static String getString(int resId) {
        return MyApplication.getApp().getResources().getString(resId);
    }

    public static String getString(int resId, Object... object) {
        return MyApplication.getApp().getResources()
                .getString(resId, object);
    }

    public static Bitmap getBitmap(int res) {
        return BitmapFactory.decodeResource(MyApplication.getApp().getResources(), res);
    }
}
