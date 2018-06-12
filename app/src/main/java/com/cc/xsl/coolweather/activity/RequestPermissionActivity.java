package com.cc.xsl.coolweather.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.receiver.FloatPermissionReceiver;
import com.cc.xsl.coolweather.util.LogUtil;
import com.cc.xsl.coolweather.util.ToastUtil;

import java.util.Arrays;

public class RequestPermissionActivity extends BaseActivity {
    private String[] permissionArray = null;
    private final int REQUEST_CODE = 1;

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
//            Uri data = intent.getData();
            LogUtil.e(intent.getDataString());
            permissionArray = intent.getStringArrayExtra(FloatPermissionReceiver.PERMISSION_ARRAY);
            LogUtil.e(Arrays.deepToString(permissionArray));

            if (permissionArray != null && permissionArray.length > 0) {
                ActivityCompat.requestPermissions(this, permissionArray, REQUEST_CODE);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        LogUtil.e("requestCode: " + requestCode + "\tpermissions: " + Arrays.deepToString(permissions)
                + "\tgrantResult: " + Arrays.toString(grantResults));
        LogUtil.e("permissionArrays: " + Arrays.deepToString(permissionArray));

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功
                    LogUtil.e("授权成功！");
                    ToastUtil.showMessage(getString(R.string.permission_success));
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // 点击拒绝授权

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionArray[0])) {
                        // 点击拒绝 再次弹出
                        ActivityCompat.requestPermissions(this, permissionArray, REQUEST_CODE);
                    } else {
                        // 选择不再询问 并点击拒绝 弹出提示框
                        new AlertDialog.Builder(this)
                                .setMessage("为了正常使用悬浮窗功能，请开启SYSTEM_ALERT_WINDOW权限")
                                .setNegativeButton(R.string.cancel, null)
                                .setPositiveButton(R.string.permission_diy, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri uri = Uri.parse("package:" + getPackageName());//包名
                                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", uri);
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                }
            }
        }
    }
}


