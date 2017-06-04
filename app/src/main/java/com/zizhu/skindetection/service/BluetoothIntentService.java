package com.zizhu.skindetection.service;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.jiqi.jiqisdk.DeviceScanCallBack;
import com.jiqi.jiqisdk.JQDeviceScanManager;
import com.zizhu.skindetection.base.BaseApplication;
import com.zizhu.skindetection.common.model.BluetoothLeClass;
import com.zizhu.skindetection.common.model.EventModel;
import com.zizhu.skindetection.common.model.ParseLeAdvData;
import com.zizhu.skindetection.common.utils.ToastUtils;
import com.zizhu.skindetection.common.utils.ZizhuHandler;

import org.greenrobot.eventbus.EventBus;

/**
 *
 */
public class BluetoothIntentService extends IntentService {

    BluetoothBinder bluetoothBinder = new BluetoothBinder();

    public BluetoothIntentService() {
        super("BluetoothIntentService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return bluetoothBinder;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public class BluetoothBinder extends Binder {

        // 蓝牙
        private ZizhuHandler mHandler;
        private BluetoothLeClass mBLE;
        private BluetoothAdapter mBluetoothAdapter;
        private Activity activity;

//        JQDeviceScanManager deviceScanManager;

        private void intHandle(Activity activity) {
            mHandler = new ZizhuHandler(activity);
            mHandler.setOnHandlerListener(new ZizhuHandler.OnHandlerListener() {
                @Override
                public void handler(Message msg) {

                }
            });
        }

        public void scanLeDevice() {
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }

        public void openBluetooth(final Activity activity) {
            this.activity = activity;
            final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = bluetoothManager.getAdapter();
            //开启蓝牙
            if (!mBluetoothAdapter.isEnabled())
                mBluetoothAdapter.enable();
            intHandle(activity);
            mBLE = new BluetoothLeClass(activity, mHandler);
            if (!mBLE.initialize()) {
                return;
            }
//            scanLeDevice();

            BaseApplication.getInstance().deviceScanManager.setScanCallBack(scanCallBack);
//            deviceScanManager=new JQDeviceScanManager(activity);
//            deviceScanManager.setScanCallBack(scanCallBack);
            new ZizhuHandler(activity).postDelayed(new Runnable() {

                @Override
                public void run() {
                    BaseApplication.getInstance().deviceScanManager.startScan();
                }
            }, 1000);
        }

        private DeviceScanCallBack scanCallBack = new DeviceScanCallBack() {

            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                new ZizhuHandler(activity).post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show("---");
                    }
                });
//                if (!TextUtils.isEmpty(device.getName())) {
////                    Log.i("json", "" + device.getName());
////                    ToastUtils.show(device.getName() + "");
//                    if (device.getName().startsWith("RENY") || device.getName().startsWith("RW")) {
////                        Log.i("json", "rssi:" + rssi + ":" + Utils.bytesToHexString(scanRecord));
//                        byte[] array = ParseLeAdvData.adv_report_parse(ParseLeAdvData.BLE_GAP_AD_TYPE_SERVICE_DATA, scanRecord);
//                        EventBus.getDefault().post(new EventModel("main", array));
//                    }
//                }
            }
        };

        private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

            @Override
            public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                if (!TextUtils.isEmpty(device.getName())) {
//                    Log.i("json", "" + device.getName());
                    if (device.getName().startsWith("RENY") || device.getName().startsWith("RW")) {
//                        Log.i("json", "rssi:" + rssi + ":" + Utils.bytesToHexString(scanRecord));
                        byte[] array = ParseLeAdvData.adv_report_parse(ParseLeAdvData.BLE_GAP_AD_TYPE_SERVICE_DATA, scanRecord);
                        EventBus.getDefault().post(new EventModel("main", array));
                    }
                }
            }
        };

        public void disconnect() {
            if (mBLE != null) {
                mBLE.disconnect();
                mBLE.close();
            }
        }
    }

}
