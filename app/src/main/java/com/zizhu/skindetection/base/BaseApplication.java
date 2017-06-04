package com.zizhu.skindetection.base;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;

import com.jiqi.jiqisdk.JQDevice;
import com.jiqi.jiqisdk.JQDeviceScanManager;
import com.zizhu.skindetection.common.utils.SystemBarTintManager;

/**
 * Created by 1 on 2016/11/16.
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    // 首页面当前选中的tab
    private int currentIndex = 0;
    Typeface lantingheiFace;
    Typeface lantingheiVeryFace;
    private SystemBarTintManager systemBarTintManager;
    private SystemBarTintManager.SystemBarConfig systemBarConfig;
    boolean hasNavigtionBar = false;
    int getNavigationBarHeight = 0;

    public JQDevice device;
    public JQDeviceScanManager deviceScanManager;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        lantingheiFace = Typeface.createFromAsset(getAssets(),"fonts/lantinghei.ttf");
        lantingheiVeryFace = Typeface.createFromAsset(getAssets(),"fonts/lantinghei_very.TTF");

        deviceScanManager=new JQDeviceScanManager(this);
    }

    public static BaseApplication getInstance() {
        if (null == baseApplication) {
            baseApplication = new BaseApplication();
        }
        return baseApplication;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Typeface getLantingheiFace() {
        return lantingheiFace;
    }

    public Typeface getLantingheiVeryFace() {
        return lantingheiVeryFace;
    }

    public SystemBarTintManager getSystemBarTintManager(Activity activity){
        if(systemBarTintManager == null){
            systemBarTintManager = new SystemBarTintManager(activity);
        }
        return systemBarTintManager;
    }

    public SystemBarTintManager.SystemBarConfig getSystemBarConfig(Activity activity){
        if(systemBarConfig == null){
            systemBarConfig = getSystemBarTintManager(activity).getConfig();
        }
        return  systemBarConfig;
    }

    public boolean hasNavigtionBar(Activity activity){
        return getSystemBarConfig(activity).hasNavigtionBar();
    }

    public int getNavigationBarHeight(Activity activity){
        return getSystemBarConfig(activity).getNavigationBarHeight();
    }
}
