package com.zizhu.skindetection.common.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.zizhu.skindetection.controller.WebBrowserActivity;

import java.io.File;
import java.io.FileOutputStream;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @auth:jeremy
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CommonUtil {

    private static InputMethodManager inputmanger;
    private static long lastClickTime = 0;
    private static long lastClickTime2 = 0;

    /**
     * 拨打电话
     *
     * @param context
     * @param phone
     */
    public static void call(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 显示键盘
     *
     * @param editText
     */
    public static void keyBoardPop(final EditText editText) {
        (new Handler() {
            public void handleMessage(Message msg) {
                InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService("input_method");
                inputManager.showSoftInput(editText, 0);
            }
        }).sendEmptyMessageDelayed(0, 200L);
    }

    //选择图片公用方法
    public static void choicePicture(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, MConstants.PHOTO_REQUEST_SELECT);
        //禁用系统动画
        activity.overridePendingTransition(0, 0);
    }

    //选择图片公用方法
    public static void choicePicture(Activity activity, Class<?> cls, int size) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("image_size", size);
        activity.startActivityForResult(intent, MConstants.PHOTO_REQUEST_SELECT);
        //禁用系统动画
        activity.overridePendingTransition(0, 0);
    }

    //选择图片公用方法
    public static void choiceFileByType(Activity activity, Class<?> cls,int mimeType,int size) {
        Intent intent=new Intent(activity, cls);
        intent.putExtra("mime_type",mimeType);
        intent.putExtra("image_size",size);
        activity.startActivityForResult(intent, MConstants.FILE_REQUEST_SELECT);
        //禁用系统动画
        activity.overridePendingTransition(0, 0);
    }

    /**
     * 剪切图片
     *
     * @param activity
     * @param uri
     */
    public static void crop(Activity activity, Uri uri,int size) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        if(size==2) {
            intent.putExtra("aspectX", 2);
            intent.putExtra("aspectY", 1);
            // 裁剪后输出图片的尺寸大小
            intent.putExtra("outputX", 400);
            intent.putExtra("outputY", 200);
        }else{
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // 裁剪后输出图片的尺寸大小
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
        }
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        activity.startActivityForResult(intent, MConstants.PHOTO_REQUEST_CUT);
    }

    public static void doubleClick() {

    }

    /**
     * 使用公共webview页面加载本地url
     *
     * @param activity
     * @param pageTitle
     * @param pageUrl
     */
    public static void startCommonBrowserForPage(Activity activity, String pageUrl) {
        Intent intent = new Intent(activity, WebBrowserActivity.class);
        intent.putExtra(WebBrowserActivity.URL_KEY, pageUrl);
        activity.startActivity(intent);
    }

    public static boolean isClickSoFast(long gapTime) {
        long currentTime = System.currentTimeMillis();
        long time = currentTime - lastClickTime;
        if (time > 0 && time < gapTime) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    public static boolean isClickSoFast2(long gapTime) {
        long currentTime = System.currentTimeMillis();
        long time = currentTime - lastClickTime2;
        if (time > 0 && time < gapTime) {
            return true;
        }
        lastClickTime2 = currentTime;
        return false;
    }

    /**
     * 【content://media】==>【file:///】
     * 将contentProvider类型Uri,转换为文件地址
     *
     * @param context
     * @param contentUri
     * @return
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Video.VideoColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Video.VideoColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private void GetandSaveCurrentImage(Activity activity) {
        String SavePATH = "";
        //1.构建Bitmap
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();

        Bitmap Bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        //2.获取屏幕
        View decorview = activity.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        Bmp = decorview.getDrawingCache();

        //3.保存Bitmap
        try {
            File path = new File(SavePATH);
            //文件
            String filepath = SavePATH + "/Screen_1.png";
            File file = new File(filepath);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();

                Toast.makeText(activity, "截屏文件已保存至SDCard/ADASiteMaps/ScreenImage/下", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
