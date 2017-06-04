package com.zizhu.skindetection.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.zizhu.skindetection.R;
import com.zizhu.skindetection.common.utils.ToastUtils;
import com.zizhu.skindetection.common.utils.ZizhuHandler;

public class StartLuncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 19) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_start_luncher);
        new ZizhuHandler(this).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartLuncherActivity.this, AppGuideActivity.class));
                finish();
            }
        }, 1500);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

}
