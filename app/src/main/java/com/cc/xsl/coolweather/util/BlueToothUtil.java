package com.cc.xsl.coolweather.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BlueToothUtil {
    private BluetoothAdapter blueAdapter;
    private BroadcastReceiver blueReceiver;
    private volatile static BlueToothUtil blueToothUtil;

    private BlueToothUtil() {
        blueAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static BlueToothUtil getInstance() {
        if (blueToothUtil == null) {
            synchronized (BlueToothUtil.class) {
                if (blueToothUtil == null) {
                    blueToothUtil = new BlueToothUtil();
                }
            }
        }
        return blueToothUtil;
    }

    /**
     * 设备是否支持蓝牙
     *
     * @return
     */
    public boolean isSupportBlue() {
        return blueAdapter != null;
    }

    /**
     * 蓝牙是否打开
     *
     * @return
     */
    public boolean isBlueEnable() {
        return isSupportBlue() && blueAdapter.isEnabled();
    }

    /**
     * 自动打开蓝牙（异步：蓝牙不会立刻就处于开启状态）
     * 这个方法打开蓝牙不会弹出提示
     */
    public void openBlueAsyn() {
        if (isSupportBlue()) {
            blueAdapter.enable();
        }
    }

    /**
     * 自动打开蓝牙（同步）
     * 这个方法打开蓝牙会弹出提示
     * 需要在onActivityResult方法中判断resultCode == RESULT_OK
     *
     * @param activity
     * @param requestCode
     */
    public void openBlueSync(Activity activity, int requestCode) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 扫描方法 返回true 扫描成功
     * 通过接收广播获取扫描到的设备
     *
     * @return
     */
    public boolean scanBlue() {
        if (!isBlueEnable()) {
            LogUtil.e("Bluetooth not enable!");
            return false;
        }
        //当前是否在扫描 如果是就取消当前的扫描 重新扫描
        if (blueAdapter.isDiscovering()) {
            blueAdapter.cancelDiscovery();
        }
        //此方法是个异步操作 一般搜索12秒
        return blueAdapter.startDiscovery();
    }

    /**
     * 取消扫描蓝牙
     *
     * @return 取消成功
     */
    public boolean cancelScanBule() {
        if (isSupportBlue()) {
            return blueAdapter.cancelDiscovery();
        }
        return true;
    }

    /**
     * 注册广播
     *
     * @param activity
     * @param callBack
     */
    public void registerReceiver(Activity activity, ScanBlueCallBack callBack) {
        blueReceiver = new ScanBlueReceiver(callBack);
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        activity.registerReceiver(blueReceiver, filter1);
        activity.registerReceiver(blueReceiver, filter2);
        activity.registerReceiver(blueReceiver, filter3);
    }

    /**
     * 取消注册广播
     *
     * @param activity
     */
    public void unRegisterReceiver(Activity activity) {
        if (blueReceiver != null) {
            activity.unregisterReceiver(blueReceiver);
        }
    }

    /**
     * 扫描广播接收类
     */
    private class ScanBlueReceiver extends BroadcastReceiver {

        private ScanBlueCallBack callback;

        public ScanBlueReceiver(ScanBlueCallBack callback) {
            this.callback = callback;
        }

        /**
         * 广播接收器 当远程蓝牙设备被发现时 回调函数onReceiver()会被执行
         *
         * @param context
         * @param intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtil.e(" scan blue receiver action: " + action);
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            switch (action) {
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED: {
                    LogUtil.e("开始扫描.....");
                    callback.onScanStarted();
                    break;
                }
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED: {
                    LogUtil.e("结束扫描.....");
                    callback.onScanFinished();
                    break;
                }
                case BluetoothDevice.ACTION_FOUND: {
                    LogUtil.e("发现设备....." + device.getAddress() + "\t" + device.getName());
                    callback.onScanning(device);
                    break;
                }
            }
        }
    }

    public interface ScanBlueCallBack {
        /**
         * 开始扫描
         */
        void onScanStarted();

        /**
         * 结束扫描
         */
        void onScanFinished();

        /**
         * 发现设备
         *
         * @param device BluetoothDevice
         */
        void onScanning(BluetoothDevice device);

    }
}
