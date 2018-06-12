package com.cc.xsl.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.cc.xsl.coolweather.util.ToastUtil;

public class FloatPermissionReceiver extends BroadcastReceiver {

    public static final String ACTION = "android.receiver.cc.xsl.floatpermissionreceiver";
    public static final String PERMISSION_ARRAY= "permission array";
    private String[] permissions = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        permissions = intent.getStringArrayExtra(PERMISSION_ARRAY);
        if (permissions != null && permissions.length > 0) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("cool://cc.xsl/request_permission"));
            i.putExtra(PERMISSION_ARRAY, permissions);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            ToastUtil.showErrorMessage();
        }
    }
}
