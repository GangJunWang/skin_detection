package com.zizhu.skindetection.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

public abstract class IActivity extends UI implements View.OnClickListener{
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewOption(getActivityLayout());
        context = this;
        initWidget();
        setView();
        bindEven();
    }

    protected abstract int getActivityLayout();

    protected abstract void setContentViewOption(int layoutId);

    protected abstract void initWidget();

    protected abstract void bindEven();

    protected abstract void setView();

    protected abstract void onClickView(View view);

    @Override
    public void onClick(View view) {
        onClickView(view);
    }
}
