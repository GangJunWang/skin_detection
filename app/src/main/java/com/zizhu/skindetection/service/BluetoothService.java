package com.zizhu.skindetection.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.jiqi.jiqisdk.DeviceListener;
import com.jiqi.jiqisdk.DeviceScanCallBack;
import com.jiqi.jiqisdk.JQDevice;
import com.jiqi.jiqisdk.JQDeviceScanManager;
import com.zizhu.skindetection.base.BaseApplication;
import com.zizhu.skindetection.common.utils.MySharedPreferences;
import com.zizhu.skindetection.common.model.Conversion;
import com.zizhu.skindetection.common.utils.ToastUtils;

/**
 * Created by 1 on 2016/12/30.
 */
public class BluetoothService extends Service implements DeviceListener {
    BaseApplication application;
    MySharedPreferences mySharedPreferences;

    private final IBinder binder = new LocalBinder();

    Handler handler = new Handler();

    String connectedMac;
    JQDeviceScanManager deviceScanManager;
    int reConnectInterval = 30 * 1000;
    @Override
    public IBinder onBind(Intent arg0) {
        return binder;
    }

    public class LocalBinder extends Binder {

        public BluetoothService getService() {

            return BluetoothService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = (BaseApplication) getApplication();
        mySharedPreferences = MySharedPreferences.getInstance(this);
        deviceScanManager = new JQDeviceScanManager(this);
        deviceScanManager.setScanCallBack(scanCallBack);
        initDevice();
    }


    @Override
    public void onGetKeyValue(int tag, byte[] bytevalue, BluetoothDevice device) {

        String values= Conversion.Bytes2HexString(bytevalue);
//        LogUtils.e(TAG, "接收数据:" + values);
    }

    private void initDevice() {
        application.device = new JQDevice(this, 0);
        application.device.setDeviceListener(this);
        connectedMac = mySharedPreferences.getBleDeviceMAC();

        if (connectedMac != null && connectedMac.length() > 0) {
            application.device.deviceStatus = 5;
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    deviceScanManager.startScan();
                }
            }, 1000);
        } else {
            application.device.deviceStatus = 0;
        }
    }

    @Override
    public void onConnected(int tag, BluetoothDevice device) {

        stopScan();

        application.device.deviceStatus = 2;
//        updateUI();
    }

    @Override
    public void onDisconnected(int tag, BluetoothDevice device) {
        application.device.deviceStatus = 5;
//        updateUI();
//        LogUtils.e(TAG, " onDisconnected");
        startReconnect();
    }

    @Override
    public void onConn(int tag, BluetoothDevice device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetDeviceName(int tag, String deviceName,
                                BluetoothDevice device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetBattery(int tag, int battery, BluetoothDevice device) {
        // TODO Auto-generated method stub
        application.device.batteryValue = battery;
    }

    @Override
    public void onGetRssi(int tag, int rssi, BluetoothDevice device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetValue(int tag, int value, BluetoothDevice device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSendSuccess(int tag, int value, BluetoothDevice device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetImageVerAndType(int tag, int ver, char type,
                                     BluetoothDevice device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetOADProgress(int tag, float progress, BluetoothDevice device) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onKeyValueSendSuccess(int tag, byte[] value,
                                      BluetoothDevice device) {
        // TODO Auto-generated method stub

    }

    public void connectDevice() {
        connectedMac = application.device.getBluetoothDevice().getAddress();
        mySharedPreferences.saveBleDeviceMAC(connectedMac);
        application.device.connect();

    }

    private void startReconnect() {
        // deviceScanManager.startScan();
//        LogUtils.e(TAG, " startReconnect ");
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // if (isSamsung) {
                // application.device.justConnect();
                //
                // LogUtils.e(TAG, " application.device.justConnect(); ");
                // } else {
                handler.removeCallbacks(reConnectRunnable);
                handler.postDelayed(reConnectRunnable, 1000);
                // }
            }
        }, 500);

    }

    private Runnable reConnectRunnable = new Runnable() {

        @Override
        public void run() {
            if (connectedMac != null && connectedMac.length() > 0) {

                if (!application.device.isConnected() && deviceScanManager.bluetoothIsEnable()) {//
                    BluetoothDevice device = deviceScanManager
                            .retrieveBluetoothDeviceWithMac(connectedMac);
                    application.device.setBluetoothDevice(device);
                    application.device.connect();

                    handler.postDelayed(this, reConnectInterval  );

                }
            }
        }
    };

    DeviceScanCallBack scanCallBack = new DeviceScanCallBack() {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

            if (connectedMac != null && connectedMac.length() > 0) {
//                Log.e(TAG, " device：" + device.getName());
                if (device.getAddress().equals(connectedMac)) {

//                    Log.e(TAG, " scan to connect");

                    deviceScanManager.stopScan();
                    application.device.setBluetoothDevice(device);

                    handler.removeCallbacks(connectRunnable);
                    handler.postDelayed(connectRunnable, 100);
                }
            }
        }
    };

    private Runnable connectRunnable = new Runnable() {

        @Override
        public void run() {
            connectDevice();
        }
    };

    public void stopScan() {
        handler.removeCallbacks(reConnectRunnable);
        deviceScanManager.stopScan();
    }

}
