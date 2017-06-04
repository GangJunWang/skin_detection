package com.zizhu.skindetection.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zizhu.skindetection.R;
import com.zizhu.skindetection.common.dialog.CommonDialog;
import com.zizhu.skindetection.common.widget.TitleBarView;


public abstract class BaseActivity extends IActivity {

    public TitleBarView titleBarView;
    protected CommonDialog commonDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonDialog = new CommonDialog(this);
        commonDialog = commonDialog.createDialog(this);
    }

    @Override
    protected void initWidget() {
        titleBarView = (TitleBarView) findViewById(R.id.title_layout);
        if (titleBarView != null) {
            titleBarView.initLeft(null, R.mipmap.icon_arrows_left, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {

        }

    }

    @Override
    protected void setContentViewOption(int resId) {
        setContentViewOption(resId, true, false);
    }

    protected void setContentViewOption(int resId, boolean hasTitle) {
        setContentViewOption(resId, hasTitle, false);
    }

    protected void setContentViewOption(int resId, boolean hasTitle, boolean hasLoadingOverLayView) {
        if (hasTitle) {
            ViewGroup root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_container_title, null);
            LayoutInflater.from(this).inflate(resId, root);
            super.setContentView(root);
        } else {
            super.setContentView(resId);
        }
    }

    public void startActivity(Class start, String key, String value){
        Intent intent = new Intent(this, start);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public void startActivity(Class start){
        Intent intent = new Intent(this, start);
        startActivity(intent);
    }
}
