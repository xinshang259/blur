package com.chris.blue.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.chris.blue.MyApplication;
import com.chris.blue.R;

public class Util {

    public static float dp2Px(float valueDP){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,valueDP, MyApplication.getApplication().getResources().getDisplayMetrics());
    }

    public static Bitmap getAvatar(int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(MyApplication.getApplication().getResources(), R.drawable.ic_test,options);
        //inJustDecodeBounds = true，然后加载图片就可以实现只解析图片的宽高信息，并不会真正的加载图片，所以这个操作是轻量级的。当获取了宽高信息，计算出缩放比后，然后在将 inJustDecodeBounds = false,再重新加载图片，就可以加载缩放后的图片。
        // inSampleSize 缩放比例
        // A：inPurgeable：设置为True时，表示系统内存不足时可以被回收，设置为False时，表示不能被回收。
        //
        //B：inInputShareable：设置是否深拷贝，与inPurgeable结合使用，inPurgeable为false时，该参数无意义。
        options.inJustDecodeBounds = false;
        options.inSampleSize  = 4;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(MyApplication.getApplication().getResources(), R.drawable.ic_test,options);
    }

}
