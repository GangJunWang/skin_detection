package com.zizhu.skindetection.controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jiqi.jiqisdk.DeviceScanCallBack;
import com.shaojun.widget.tablayout.CommonTabLayout;
import com.shaojun.widget.tablayout.listener.CustomTabEntity;
import com.shaojun.widget.tablayout.listener.OnTabSelectListener;
import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.BaseApplication;
import com.zizhu.skindetection.base.activity.BaseActivity;
import com.zizhu.skindetection.common.model.EventModel;
import com.zizhu.skindetection.common.model.ParseLeAdvData;
import com.zizhu.skindetection.common.model.Utils;
import com.zizhu.skindetection.common.utils.CommonUtil;
import com.zizhu.skindetection.common.utils.MConstants;
import com.zizhu.skindetection.common.utils.PermissionUtil;
import com.zizhu.skindetection.common.utils.ToastUtils;
import com.zizhu.skindetection.common.utils.ZizhuHandler;
import com.zizhu.skindetection.controller.fragment.MainFragmen;
import com.zizhu.skindetection.controller.fragment.RecordFragment;
import com.zizhu.skindetection.controller.fragment.SelfTakeFragment;
import com.zizhu.skindetection.controller.fragment.TabEntity;
import com.zizhu.skindetection.service.BluetoothService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends BaseActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"记录", "首页", "自拍"};
    private int[] mIconUnselectIds = {R.mipmap.home_page_nav_record, R.mipmap.home_page_nav_home, R.mipmap.home_page_nav_autodyne};
    private int[] mIconSelectIds = {R.mipmap.home_page_nav_record_sel, R.mipmap.home_page_nav_home_sel, R.mipmap.home_page_nav_autodyne_sel};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<CustomTabEntity>();
    private CommonTabLayout mainTabLayout;
    private Fragment currentFragment;
    private View v_01;

    BaseApplication application;
    private BluetoothService mainService;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        titleBarView.setVisibility(View.GONE);
        mainTabLayout = (CommonTabLayout) findViewById(R.id.mainTabLayout);
        v_01 = findViewById(R.id.v_01);

        //tabs
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mFragments.add(RecordFragment.newInstance());
        mFragments.add(MainFragmen.newInstance());
        mFragments.add(SelfTakeFragment.newInstance());
        mainTabLayout.setTabData(mTabEntities);
        if(PermissionUtil.hasPermission(this, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN)){
            openBlue();
            bind();
        }else {
            PermissionUtil.requestPermission(this, MConstants.OPEN_BLUETOOTH,
                    Manifest.permission.CALL_PHONE);
        }
    }

    @Override
    protected void bindEven() {
        mainTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    protected void setView() {
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        switchTab(1);
        EventBus.getDefault().register(this);
        if(BaseApplication.getInstance().hasNavigtionBar(this)){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v_01.getLayoutParams();
            layoutParams.height = BaseApplication.getInstance().getNavigationBarHeight(this);
            v_01.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onClickView(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mConnection!=null){
            unbindService(mConnection);
        }
    }

    private void bindMainService() {
        Intent intent=new Intent(this, BluetoothService.class);
        startService(intent);
        bindService(intent,  mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mainService = ((BluetoothService.LocalBinder) service) .getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mainService = null;
        }

    };

    private void startScan() {
        countDownTimer.start();
    }

    private void switchTab(int currIndex) {
        BaseApplication.getInstance().setCurrentIndex(currIndex);
        mainTabLayout.setCurrentTab(currIndex);
        switchFragment(mFragments.get(currIndex));
    }

    /**
     * @param targetFragment
     */
    private void switchFragment(Fragment targetFragment) {
        if (targetFragment == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        //第一次使用switchFragment()时currentFragment为null，所以要判断一下
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        //如已添加过，则show就行了
        if (targetFragment.isAdded()) {
            transaction.show(targetFragment);
        } else {
            transaction.add(R.id.content_frame, targetFragment);
        }
        transaction.commit();
        currentFragment = targetFragment;
    }

    boolean canEnter = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(EventModel eventModel){
        if(eventModel != null){
            if("main".equals(eventModel.type)){
                final byte[] array = (byte[]) eventModel.eventBus;
                Log.i("json", "array:" + Utils.bytesToHexString(array));
//                ToastUtils.show(Utils.bytesToHexString(array) + "");
                if(!TextUtils.isEmpty(Utils.bytesToHexString(array))){
                    if(BaseApplication.getInstance().getCurrentIndex() == 2) {
                        if (Utils.bytesToHexString(array).toLowerCase().indexOf("01ffff00") != -1
                                || Utils.bytesToHexString(array).toLowerCase().indexOf("00ffff01") != -1) {
                            if (!CommonUtil.isClickSoFast(5000)) {
                                ((SelfTakeFragment) currentFragment).takePicture(new EventModel("take", array));
                            }
                        }
                    } else{
                        if (Utils.bytesToHexString(array).toLowerCase().indexOf("01ffff00") != -1
                                || Utils.bytesToHexString(array).toLowerCase().indexOf("00ffff01") != -1) {
                            if (!CommonUtil.isClickSoFast(5000)) {
                                EventBus.getDefault().post(new EventModel("start", array));
                                canEnter = true;
                            }
                        } else if(Utils.bytesToHexString(array).toLowerCase().indexOf("00ffff00") != -1
                                || Utils.bytesToHexString(array).toLowerCase().indexOf("00ffff01") != -1
                                || Utils.bytesToHexString(array).toLowerCase().indexOf("11ffff") != -1){
                            if (!CommonUtil.isClickSoFast(5000) && canEnter) {
                                EventBus.getDefault().post(new EventModel("error", array));
                                canEnter = false;
                            }
                        }else if(Utils.bytesToHexString(array).toLowerCase().indexOf("01ffff00") == -1
                                && Utils.bytesToHexString(array).toLowerCase().indexOf("ffff") == -1){
                            if (!CommonUtil.isClickSoFast2(5000) && canEnter) {
                                if((array[3] & 0xFF) == 0 || (array[4] & 0xFF) == 0){
                                    EventBus.getDefault().post(new EventModel("error", array));
                                    return;
                                }
                                EventBus.getDefault().post(new EventModel("read", array));
                                canEnter = false;
                            }else if(!CommonUtil.isClickSoFast2(5000)){
                                EventBus.getDefault().post(new EventModel("start", array));
                                canEnter = false;
                                mainTabLayout.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if((array[3] & 0xFF) == 0 || (array[4] & 0xFF) == 0){
                                            EventBus.getDefault().post(new EventModel("error", array));
                                            return;
                                        }
                                        EventBus.getDefault().post(new EventModel("read", array));
                                    }
                                }, 5000);
                            }
                        }
                    }
                }
            }
        }
    }

    private DeviceScanCallBack scanCallBack=new DeviceScanCallBack() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, final byte[] scanRecord) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (!TextUtils.isEmpty(device.getName())) {
                        if (device.getName().startsWith("RENY") || device.getName().startsWith("RW")) {
                            byte[] array = ParseLeAdvData.adv_report_parse(ParseLeAdvData.BLE_GAP_AD_TYPE_SERVICE_DATA, scanRecord);
                            EventBus.getDefault().post(new EventModel("main", array));
                        }
                    }
                }
            });
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MConstants.OPEN_BLUETOOTH:{
                if(PermissionUtil.hasPermission(this, Manifest.permission.CALL_PHONE)){
                    openBlue();
                    bind();
                }else{
                    ToastUtils.show("您已拒绝使用蓝牙权限");
                }
            }
            break;
        }
    }

    void openBlue(){
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBluetoothAdapter = bluetoothManager.getAdapter();
        }
        //开启蓝牙
        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled())
            mBluetoothAdapter.enable();
    }

    /**
     * 启动蓝牙服务
     */
    void bind(){
        application = (BaseApplication) getApplication();
        application.deviceScanManager.setScanCallBack(scanCallBack);
        bindMainService();
        new ZizhuHandler(this).postDelayed(new Runnable() {
            @Override
            public void run() {
                startScan();
            }
        }, 1000);
    }

    CountDownTimer countDownTimer = new CountDownTimer(30, 15) {
        @Override
        public void onTick(long l) {
            application.deviceScanManager.startScan();
        }

        @Override
        public void onFinish() {

        }
    };

}
