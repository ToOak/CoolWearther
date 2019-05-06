package com.cc.xsl.coolweather.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.ScanCallback;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class BlueToothUtil {
    //这条是蓝牙串口通用的UUID，不要更改
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter blueAdapter;
    private BroadcastReceiver blueReceiver;
    private PinBlueReceiver pinBlueReceiver;
    private static BluetoothSocket bluetoothSocket;
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
        if (pinBlueReceiver != null) {
            activity.unregisterReceiver(pinBlueReceiver);
        }
    }

    /**
     * 配对（配对成功与失败通过广播返回）
     *
     * @param device
     */
    public void pinBlue(BluetoothDevice device) {
        if (device == null) {
            LogUtil.e("bond device null");
            return;
        }
        if (!isBlueEnable()) {
            LogUtil.e("Bluetooth not enable!");
            return;
        }
        //配对之前把扫描关闭
        if (blueAdapter.isDiscovering()) {
            blueAdapter.cancelDiscovery();
        }
        //判断设备是否配对 没有配对再配 配对了就不需要配了
        if (device.getBondState() == BluetoothDevice.BOND_NONE) {
            LogUtil.e("attemp to bond: " + device.getName());
            try {
                Method createBondMethod = device.getClass().getMethod("createBond");
                Boolean returnVale = (Boolean) createBondMethod.invoke(device);
                LogUtil.e("pin createBond: " + returnVale);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e("attemp to bond fail");
            }
        }
    }

    /**
     * 注册配对广播
     *
     * @param activity
     */
    public void registerPinReceiver(Activity activity, PinBlueCallBack callBack) {
        pinBlueReceiver = new PinBlueReceiver(callBack);
        IntentFilter filter4 = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);
        IntentFilter filter5 = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        activity.registerReceiver(pinBlueReceiver, filter4);
        activity.registerReceiver(pinBlueReceiver, filter5);
    }

    /**
     * 取消配对（取消配对成功与失败通过广播返回）
     *
     * @param device
     */
    public void cancelPinBlue(BluetoothDevice device) {
        if (device == null) {
            LogUtil.e("cancel bond device null");
            return;
        }
        if (!isBlueEnable()) {
            LogUtil.e("Bluetooth not enable!");
            return;
        }
        if (device.getBondState() != BluetoothDevice.BOND_NONE) {
            LogUtil.e("attemp to cancel bond: " + device.getName());
            try {
                Method removeBondMethod = device.getClass().getMethod("removeBond");
                Boolean returnValue = (Boolean) removeBondMethod.invoke(device);
                LogUtil.e("cancelPinBlue removeBond: " + returnValue);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e("attemp to cancel bond fail!");
            }
        }
    }

    /**
     * 连接（在配对之后调用）
     *
     * @param device
     * @param callBack
     */
    public void connect(BluetoothDevice device, ConnectBlueCallBack callBack) {
        if (device == null) {
            LogUtil.e("bond device null");
            return;
        }
        if (!isBlueEnable()) {
            LogUtil.e("Bluetooth not enable!");
            return;
        }
        //连接之前把扫描关闭
        if (blueAdapter.isDiscovering()) {
            blueAdapter.cancelDiscovery();
        }
        new ConnectBlueTask(callBack).execute(device);
    }

    /**
     * 蓝牙是否连接
     *
     * @return
     */
    public boolean isConnectBlue() {
        return bluetoothSocket != null && bluetoothSocket.isConnected();
    }

    /**
     * 断开连接
     *
     * @return
     */
    public boolean cancelConnect() {
        if (bluetoothSocket != null && bluetoothSocket.isConnected()) {
            try {
                bluetoothSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        bluetoothSocket = null;
        return true;
    }

    /**
     * 输入mac地址进行自动配对
     * 前提是系统保存了该地址的对象
     *
     * @param address
     * @param callBack
     */
    public void connectMAC(String address, ConnectBlueCallBack callBack) {
        if (!isBlueEnable()) {
            return;
        }
        BluetoothDevice btDev = blueAdapter.getRemoteDevice(address);
        connect(btDev, callBack);
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

    /**
     * 配对广播接收类
     */
    private class PinBlueReceiver extends BroadcastReceiver {
        /**
         * 此处为你要连接的蓝牙设备的初始密钥 一般为1234或0000
         */
        private String pin = "0000";
        private PinBlueCallBack callBack;

        public PinBlueReceiver(PinBlueCallBack callBack) {
            this.callBack = callBack;
        }

        /**
         * 广播接收器  当远程蓝牙设备被发现时 回调函数onReceiver()会被执行
         *
         * @param context
         * @param intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtil.e("PinBlueReceiver action: " + action);
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
                try {
                    callBack.onBondRequest();
                    //1、确认配对
                    Method setPairingConfirmation = device.getClass().getDeclaredMethod("setPairingConfirmation", boolean.class);
                    setPairingConfirmation.invoke(device, true);
                    //2、中止有序广播
                    LogUtil.e("isOrderedBroadcast: " + isOrderedBroadcast() + ", isInitialStickyBroadcast: " + isInitialStickyBroadcast());
                    abortBroadcast();//如果没有将广播终止 则会出现一个一闪而过的配对框
                    //3、调用setPin方法进行配对...
                    Method removeBondMethod = device.getClass().getDeclaredMethod("setPin", byte[].class);
                    Boolean returnValue = (Boolean) removeBondMethod.invoke(device, new Object[]{pin.getBytes()});
                    LogUtil.e("action pairing request: " + returnValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_NONE: {
                        LogUtil.e("取消配对");
                        callBack.onBondFail(device);
                        break;
                    }
                    case BluetoothDevice.BOND_BONDING: {
                        LogUtil.e("配对中");
                        callBack.onBonding(device);
                        break;
                    }
                    case BluetoothDevice.BOND_BONDED: {
                        LogUtil.e("配对成功");
                        callBack.onBondSuccess(device);
                        break;
                    }
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

    public interface PinBlueCallBack {
        void onBondRequest();

        /**
         * 取消配对
         *
         * @param device
         */
        void onBondFail(BluetoothDevice device);

        /**
         * 配对中
         *
         * @param device
         */
        void onBonding(BluetoothDevice device);

        /**
         * 配对成功
         *
         * @param device
         */
        void onBondSuccess(BluetoothDevice device);
    }

    public interface ConnectBlueCallBack {
        /**
         * 开始连接
         */
        void onStartConnect();

        /**
         * 连接成功
         *
         * @param device
         * @param socket
         */
        void onConnectSuccess(BluetoothDevice device, BluetoothSocket socket);

        /**
         * 连接失败
         *
         * @param device
         * @param msg
         */
        void onConnectFail(BluetoothDevice device, String msg);
    }

    public interface ReadCallBack {
        /**
         * 开始读取数据
         */
        void onStarted();

        /**
         * 完成读取数据
         *
         * @param isSuccess
         * @param msg
         */
        void onFinished(boolean isSuccess, String msg);
    }

    public interface WriteCallBack {
        /**
         * 开始发送数据
         */
        void onStarted();

        /**
         * 完成数据发送
         *
         * @param isSuccess
         * @param msg
         */
        void onFinished(boolean isSuccess, String msg);
    }

    /**
     * 连接线程
     */
    private static class ConnectBlueTask extends AsyncTask<BluetoothDevice, Integer, BluetoothSocket> {

        private BluetoothDevice bluetoothDevice;
        private ConnectBlueCallBack callBack;

        ConnectBlueTask(ConnectBlueCallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        protected BluetoothSocket doInBackground(BluetoothDevice... bluetoothDevices) {
            bluetoothDevice = bluetoothDevices[0];
            BluetoothSocket socket = null;
            try {
                LogUtil.e("开始连接socket，uuid：" + MY_UUID.toString());
                socket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
                if (socket != null && !socket.isConnected()) {
                    socket.connect();
                }
            } catch (Exception e) {
                LogUtil.e("socket连接失败");
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    LogUtil.e("socket关闭失败");
                }
                e.printStackTrace();
            }
            return socket;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LogUtil.e("开始连接");
            if (callBack != null) {
                callBack.onStartConnect();
            }
        }

        @Override
        protected void onPostExecute(BluetoothSocket socket) {
            super.onPostExecute(socket);
            if (socket != null && socket.isConnected()) {
                LogUtil.e("连接成功");
                if (callBack != null) {
                    bluetoothSocket = socket;
                    callBack.onConnectSuccess(bluetoothDevice, socket);
                }
            } else {
                LogUtil.e("连接失败");
                if (callBack != null) {
                    callBack.onConnectFail(bluetoothDevice, "连接失败");
                }
            }
        }
    }

    /**
     * 读取数据线程
     */
    public static class ReadTask extends AsyncTask<String, Integer, String> {

        private ReadCallBack callBack;
        private BluetoothSocket socket;

        public ReadTask(ReadCallBack callBack, BluetoothSocket socket) {
            this.callBack = callBack;
            this.socket = socket;
        }

        @Override
        protected String doInBackground(String... strings) {
            BufferedInputStream in = null;
            try {
                StringBuffer sb = new StringBuffer();
                in = new BufferedInputStream(socket.getInputStream());

                int length = 0;
                byte[] buf = new byte[1024];
                while ((length = in.read()) != -1) {
                    sb.append(new String(buf, 0, length));
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "读取失败";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LogUtil.e("开始读取设备");
            if (callBack != null) {
                callBack.onStarted();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            LogUtil.e("完成读取数据");
            if (callBack != null) {
                if ("读取失败".equals(s)) {
                    callBack.onFinished(false, s);
                } else {
                    callBack.onFinished(true, s);
                }
            }
        }
    }

    /**
     * 写入数据线程
     */
    public static class WriteTask extends AsyncTask<String, Integer, String> {

        private WriteCallBack callBack;
        private BluetoothSocket socket;

        public WriteTask(WriteCallBack callBack, BluetoothSocket socket) {
            this.callBack = callBack;
            this.socket = socket;
        }

        @Override
        protected String doInBackground(String... strings) {
            String string = strings[0];
            OutputStream outputStream = null;
            try {
                outputStream = socket.getOutputStream();
                outputStream.write(string.getBytes());
            } catch (IOException e) {
                LogUtil.e("Exception during write: " + e.getMessage());
                e.printStackTrace();
                return "发送失败";
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "发送成功";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (callBack != null) {
                callBack.onStarted();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (callBack != null) {
                if ("发送成功".equals(s)) {
                    callBack.onFinished(true, s);
                } else {
                    callBack.onFinished(false, s);
                }
            }
        }
    }
}
