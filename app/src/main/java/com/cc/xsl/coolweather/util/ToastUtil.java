package com.cc.xsl.coolweather.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.xsl.coolweather.MyApplication;
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
//        Handler一定要在主线程实例化吗?new Handler()和new Handler(Looper.getMainLooper())的区别
//        如果你不带参数的实例化：Handler handler = new Handler();那么这个会默认用当前线程的looper
//        一般而言，如果你的Handler是要来刷新操作UI的，那么就需要在主线程下跑。
//        情况:
//        1.要刷新UI，handler要用到主线程的looper。那么在主线程 Handler handler = new Handler();，如果在其他线程，也要满足这个功能的话，要Handler handler = new Handler(Looper.getMainLooper());
//        2.不用刷新ui,只是处理消息。 当前线程如果是主线程的话，Handler handler = new Handler();不是主线程的话，Looper.prepare(); Handler handler = new Handler();Looper.loop();或者Handler handler = new Handler(Looper.getMainLooper());
//        若是实例化的时候用Looper.getMainLooper()就表示放到主UI线程去处理。
//        如果不是的话，因为只有UI线程默认Loop.prepare();Loop.loop();过，其他线程需要手动调用这两个，否则会报错。
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
        showMessage(MyApplication.getApp(), msgId, Toast.LENGTH_SHORT);
    }

    public static void showMessage(String msg) {
        showMessage(MyApplication.getApp(), msg, Toast.LENGTH_SHORT);
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
        View view = LayoutInflater.from(MyApplication.getApp()).inflate(R.layout.toast_layout, null);

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
            toast = new Toast(MyApplication.getApp());
            toast.setView(view);
            show();
            Looper.loop();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            toast = new Toast(MyApplication.getApp());
            toast.setView(view);
            show();
        }
    }
}
