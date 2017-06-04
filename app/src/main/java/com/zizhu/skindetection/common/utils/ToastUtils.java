package com.zizhu.skindetection.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.zizhu.skindetection.base.BaseApplication;


public class ToastUtils {
    private static Toast toast;

    public static void show(final String msg) {
        showMessage(BaseApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void show(final int msg) {
        showMessage(BaseApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void show(final String msg, final int duration) {
        if (duration == 0) {
            showMessage(BaseApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            showMessage(BaseApplication.getInstance().getApplicationContext(), msg, duration);
        }
    }

    public static void showMessage(final Context act, final String msg,
                                   final int len) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(act, msg, len);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showMessage(final Context act, final int msg,
                                   final int len) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(act, msg, len);
        toast.show();
    }

}
