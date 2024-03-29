package com.zizhu.skindetection.base.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.zizhu.skindetection.R;
import com.zizhu.skindetection.common.dialog.CommonDialog;
import com.zizhu.skindetection.common.utils.ToastUtils;
import com.zizhu.skindetection.common.widget.TitleBarView;
import com.zizhu.skindetection.common.widget.swipeback.SwipeBackLayout;
import com.zizhu.skindetection.common.widget.swipeback.SwipeBackUtils;
import com.zizhu.skindetection.common.widget.swipeback.app.SwipeBackActivityBase;
import com.zizhu.skindetection.common.widget.swipeback.app.SwipeBackActivityHelper;

import java.util.Map;

public abstract class BaseBackActivity extends IActivity implements SwipeBackActivityBase {
    private static Handler handler;
    public TitleBarView titleBarView;
    private ProgressDialog progressDialog;
    private SwipeBackActivityHelper mHelper;
    private boolean destroyed = false;
    protected CommonDialog commonDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
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

    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.show();
    }

    /**
     * 取消ProgressView
     */
    public void dismissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void showToast(String text) {
        this.showToast(text, 0);
    }

    public void showToast(String text, int duration) {
        ToastUtils.show(text, duration);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (isShow) {
            if (getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(getCurrentFocus(), 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }

    public void startActivity(Class start, Map<String, String> map){
        Intent intent = new Intent(this, start);
        for(Map.Entry<String, String> entry : map.entrySet()){
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        startActivity(intent);
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

    /**
     * 延时弹出键盘
     *
     * @param focus ：键盘的焦点项
     */
    protected void showKeyboardDelayed(View focus) {
        final View viewToFocus = focus;
        if (focus != null) {
            focus.requestFocus();
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (viewToFocus == null || viewToFocus.isFocused()) {
                    showKeyboard(true);
                }
            }
        }, 200);
    }

    /**
     * 判断页面是否已经被销毁（异步回调时使用）
     */
    public boolean isDestroyedCompatible() {
        if (Build.VERSION.SDK_INT >= 17) {
            return isDestroyedCompatible17();
        } else {
            return destroyed || super.isFinishing();
        }
    }

    @TargetApi(17)
    private boolean isDestroyedCompatible17() {
        return super.isDestroyed();
    }

    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }

    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }

}
