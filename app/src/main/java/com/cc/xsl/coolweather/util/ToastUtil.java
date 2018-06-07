package com.cc.xsl.coolweather.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.xsl.coolweather.BaseApplication;
import com.cc.xsl.coolweather.R;

/**
 * Created by xushuailong on 2016/10/10.
 */
public class ToastUtil {

    private static final Object synObj = new Object();

    private static volatile Toast toast = null;

    public static void showMessage(final Context act, final int msgId) {
        showMessage(act, msgId, Toast.LENGTH_SHORT);
    }


    public static void showMessage(final Context act, final int msgId,
                                   final int len) {
        String str = ResUtil.getString(msgId);
        showMessage(act, str, len);
    }

    public static void showMessage(final Context act, final String msg) {
        showMessage(act, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(final Context act, final String msg,
                                   final int len) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
        // TODO 有什么意义 以上 了解一下？ 提示：子线程没有自己的Looper
        if (Looper.myLooper() == null) {
            Looper.prepare();
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(act, msg, len);
            show();
            Looper.loop();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(act, msg, len);

            show();
        }
    }

    public static void showMessage(final int msgId) {
        showMessage(BaseApplication.getApp(), msgId, Toast.LENGTH_SHORT);
    }

    public static void showMessage(String msg) {
        showMessage(BaseApplication.getApp(), msg, Toast.LENGTH_SHORT);
    }

    private static void show() {
        synchronized (synObj) {
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void showErrorMessage() {
        showErrorMessage(null, null);
    }

    public static void showErrorMessage(String title, String content) {
        View view = LayoutInflater.from(BaseApplication.getApp()).inflate(R.layout.toast_layout, null);

        if (!TextUtils.isEmpty(title)) {
            ((TextView) view.findViewById(R.id.toast_title)).setText(title);
            view.findViewById(R.id.toast_title).setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(content)) {
            ((TextView) view.findViewById(R.id.toast_content)).setText(content);
        }
        if (Looper.myLooper() == null) {
            Looper.prepare();
            if (toast != null) {
                toast.cancel();
            }
            toast = new Toast(BaseApplication.getApp());
            toast.setView(view);
            show();
            Looper.loop();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            toast = new Toast(BaseApplication.getApp());
            toast.setView(view);
            show();
        }
    }
}
