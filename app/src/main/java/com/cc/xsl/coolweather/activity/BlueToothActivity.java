package com.cc.xsl.coolweather.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.util.BlueToothUtil;
import com.cc.xsl.coolweather.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class BlueToothActivity extends BaseActivity {

    private static final int REQUEST_CODE_PERMISSION_LOCATION = 11;
    private static final int REQUEST_CODE_OPEN_GPS = 12;
    private static final int REQUEST_OPEN_BLUE_SYNC = 13;

    public static Intent getAction(Context context) {
        return new Intent(context, BlueToothActivity.class);
    }

    private TextView textView;

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        textView = findViewById(R.id.blue_device_tv);
        checkPermissions();
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_blue_tooth;
    }

    /**
     * 检查权限
     */
    private void checkPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(this, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            onPermissionGranted(permissions[i]);
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * 开启GPS
     *
     * @param permission
     */
    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("当前手机扫描蓝牙需要打开定位功能。")
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                            .setPositiveButton("前往设置",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                                        }
                                    })
                            .setCancelable(false)
                            .show();
                } else {
                    //GPS已经开启了
                    LogUtil.e("是否支持蓝牙：" + BlueToothUtil.getInstance().isSupportBlue());
                    LogUtil.e("蓝牙是否开启：" + BlueToothUtil.getInstance().isBlueEnable());
                    BlueToothUtil.getInstance().registerReceiver(this, new BlueToothUtil.ScanBlueCallBack() {
                        @Override
                        public void onScanStarted() {

                        }

                        @Override
                        public void onScanFinished() {

                        }

                        @Override
                        public void onScanning(BluetoothDevice device) {
                            textView.setText(textView.getText().toString() + device.getAddress() + " : " + device.getName() + "\n");
                        }
                    });
//                    BlueToothUtil.getInstance().openBlueAsyn();
//                    BlueToothUtil.getInstance().scanBlue();
                    BlueToothUtil.getInstance().openBlueSync(this, REQUEST_OPEN_BLUE_SYNC);
                }
                break;
            }
        }
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_OPEN_GPS: {
                onPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
                break;
            }
            case REQUEST_OPEN_BLUE_SYNC: {
                LogUtil.e("result code: " + resultCode);
                if (BlueToothUtil.getInstance().isBlueEnable()) {
                    BlueToothUtil.getInstance().scanBlue();
                } else {
                    BlueToothUtil.getInstance().openBlueSync(this, REQUEST_OPEN_BLUE_SYNC);
                }
                break;
            }
        }
    }
}
