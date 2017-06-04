package com.zizhu.skindetection.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public Context context;
    public View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutId(), null);
        setRootView(root);
        return getRootView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initWidget(getRootView());
        bindEven();
        setView();
    }
    protected abstract int getLayoutId();

    protected abstract void initWidget(View rootView);

    protected abstract void bindEven();

    protected abstract void setView();

    protected abstract void onClickView(View view);

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public void showToast(String text) {
        this.showToast(text, 0);
    }

    public void showToast(String text, int duration) {
        Toast.makeText(getActivity(), text + "", duration).show();
    }

    @Override
    public void onClick(View view) {
        onClickView(view);
    }


    public void startActivity(Class start, String key, String value){
        Intent intent = new Intent(getActivity(), start);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public void startActivity(Class start){
        Intent intent = new Intent(getActivity(), start);
        startActivity(intent);
    }
}
