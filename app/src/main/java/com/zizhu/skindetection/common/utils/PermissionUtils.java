package com.zizhu.skindetection.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by 1 on 2017/1/5.
 */
public class PermissionUtils {

    /**
     * 为子类提供一个权限检查方法
     * @param permissions
     * @return
     */
    public static boolean hasPermission(Context context, String... permissions){
        if(permissions==null || permissions.length==0){
            return false;
        }
        for (String permission: permissions){
            if(ContextCompat.checkSelfPermission(context, permission)
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
    public static void requestPermission(Activity context, int requestCode, String... permissons){
        ActivityCompat.requestPermissions(context, permissons, requestCode);
    }

}
