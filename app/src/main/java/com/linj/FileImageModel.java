package com.linj;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by 1 on 2016/11/27.
 */
public class FileImageModel implements Serializable{
    public Bitmap bitmap;
    public String filePath;
    public String thumbPath;
}
