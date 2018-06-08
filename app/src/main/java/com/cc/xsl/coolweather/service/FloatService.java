package com.cc.xsl.coolweather.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.xsl.coolweather.BaseApplication;
import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.receiver.FloatPermissionReceiver;
import com.cc.xsl.coolweather.util.Config;
import com.cc.xsl.coolweather.util.LogUtil;
import com.cc.xsl.coolweather.util.ScreenUtil;
import com.cc.xsl.coolweather.util.SharedPreUtil;

public class FloatService extends Service {

    private final String alertWindoPermission = Manifest.permission.SYSTEM_ALERT_WINDOW;

    private View realFloatView = null;
    private View floatView = null;
    WindowManager windowManager = null;
    private boolean realShowFlag = false;
    private Handler handler = new Handler();
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            dismissRealFloat();
        }
    };

    @Override
    public void onCreate() {
        LogUtil.e("onCreate");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        LogUtil.e("onStart");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                /**
                 *  TODO Caused by: android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
                 *  Context中有一个startActivity方法，Activity继承自Context，重载了startActivity方法。如果使用Activity的startActivity方法，不会有任何限制，
                 *  而如果使用Context的startActivity方法的话，就需要开启一个新的task，遇到上面那个异常的，都是因为使用了Context的startActivity方法。解决办法是，加一个flag。
                 *  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 */
                Intent i = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BaseApplication.getApp().startActivity(i);
            } else {
                showFloat();
            }
        }

        // TODO 对于 SYSTEM_ALERT_WINDOW 权限的请求  以上方法可行
        // 以下方法在coolpad changer s1上调起的应用详情界面未找到类似华为的“高级——>在其他应用的上层显示”该项设置
        // 但是以下在华为上授权后依然显示授权不成功 为虾米？？？？
//        requestPermission();

        super.onStart(intent, startId);
        SharedPreUtil.getInstance().put(Config.IS_FLOAT_ALREADY, true);
        stopSelf();
    }

    private void requestPermission() {
        boolean permission = ActivityCompat.checkSelfPermission(this, alertWindoPermission)
                == PackageManager.PERMISSION_GRANTED;
        LogUtil.e("requset " + permission);
        if (!permission) {
            Intent permissionIntent = new Intent(FloatPermissionReceiver.ACTION);
            permissionIntent.putExtra(FloatPermissionReceiver.PERMISSION_ARRAY, new String[]{alertWindoPermission});
            sendBroadcast(permissionIntent);
        } else {
            showFloat();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.e("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtil.e("onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.e("onBind");
        return null;
    }

    private void showFloat() {
        if (floatView == null) {
            floatView = LayoutInflater.from(this).inflate(R.layout.layout_float, null);
        }
        if (windowManager == null) {
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        }
        if (windowManager != null) {
            /**
             *  java.lang.RuntimeException:
             *  java.lang.IllegalArgumentException:
             *  View=android.widget.LinearLayout{c1e0e52 V.E...... ......I. 0,0-0,0} not attached to window manager
             */
            try {
                windowManager.removeView(floatView);
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
                e.printStackTrace();
            }
        }

        /**
         * WindowManager.LayoutParams.TYPE_PHONE
         * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
         */
        //类型是TYPE_TOAST，像一个普通的Android Toast一样。这样就不需要申请悬浮窗权限了。
        WindowManager.LayoutParams params =
                new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);
        //初始化后不首先获得窗口焦点。不妨碍设备上其他部件的点击、触摸事件。
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.alpha = 0.5f;

        floatView.setBackgroundColor(Color.TRANSPARENT);

        ImageView floatControlIv = (ImageView) floatView.findViewById(R.id.float_controller_iv);
        final View floatContentView = floatView.findViewById(R.id.float_content_ll);
        final Button goHomeBtn = (Button) floatView.findViewById(R.id.go_home_btn);

        floatControlIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (floatContentView.getVisibility() == View.VISIBLE) {
//                    floatContentView.setVisibility(View.GONE);
//                    floatView.invalidate();
//                } else if (floatContentView.getVisibility() == View.GONE) {
//                    floatContentView.setVisibility(View.VISIBLE);
//                    floatView.invalidate();
//                }
                if (realShowFlag) {
                    dismissRealFloat();
                } else {
                    showRealFloat();
                }
            }
        });

        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
                floatContentView.setVisibility(View.GONE);
//                floatView.invalidate();
            }
        });
        if (windowManager != null) {
            windowManager.addView(floatView, params);
        }

    }

    private void dismissRealFloat() {
        if (windowManager == null) {
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        }
        if (windowManager != null) {
            try {
                windowManager.removeView(realFloatView);
                realShowFlag = false;
                handler.removeCallbacks(r);
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void showRealFloat() {
        if (realFloatView == null) {
            realFloatView = LayoutInflater.from(this).inflate(R.layout.layout_real_float, null);
        }

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        //初始化后不首先获得窗口焦点。不妨碍设备上其他部件的点击、触摸事件。
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.y = 108;
        params.alpha = 0.8f;
        params.x = 0;


        TextView goHmoeBtn = (TextView) realFloatView.findViewById(R.id.go_home_tv);
        goHmoeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
                dismissRealFloat();
            }
        });

        if (windowManager != null) {
            windowManager.addView(realFloatView, params);
            realShowFlag = true;
            handler.postDelayed(r, 5000);
        }

    }

    private void goHome() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}
