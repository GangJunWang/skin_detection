package com.zizhu.skindetection.common.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2016/10/8.
 */

public class PermissionUtil {
    /**
     * 为子类提供一个权限检查方法
     * @param permissions
     * @return
     */
    public static boolean hasPermission(Activity activity, String... permissions){
        if(!isOverMarshmallow()) {
            return true;
        }
        if(permissions==null || permissions.length==0){
            return false;
        }
        for (String permission: permissions){
            if(ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    /**
     * 为子类提供一个权限请求方法
     * @param requestCode
     * @param permissons
     */
    public static void requestPermission(Activity activity, int requestCode, String... permissons){
        ActivityCompat.requestPermissions(activity, permissons, requestCode);
    }

    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}

