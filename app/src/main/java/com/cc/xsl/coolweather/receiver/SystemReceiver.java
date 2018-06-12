package com.cc.xsl.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.cc.xsl.coolweather.service.FloatService;
import com.cc.xsl.coolweather.util.Config;
import com.cc.xsl.coolweather.util.SharedPreUtil;
import com.cc.xsl.coolweather.util.ToastUtil;

public class SystemReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.contains(".")) {
                ToastUtil.showMessage(action.substring(action.lastIndexOf(".") + 1));
            }
        }
//        if (!SharedPreUtil.getInstance().getBoolean(Config.IS_FLOAT_ALREADY)) {
            context.startService(new Intent(context, FloatService.class));
//        }
    }
}
