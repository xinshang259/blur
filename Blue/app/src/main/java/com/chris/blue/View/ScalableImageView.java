package com.chris.blue.View;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import com.chris.blue.R;
import com.chris.blue.utils.Util;

public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    public static final float IMAGE_WIDTH = Util.dp2Px(300);

    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    GestureDetectorCompat gestureDetectorCompat;

    boolean big;

    float scaleFraction;
    private float smallScale;
    private float bigScale;
    private float offsetX;
    private float offsetY;

    ObjectAnimator scaleAnimator;


    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Util.getAvatar((int) IMAGE_WIDTH);
        gestureDetectorCompat = new GestureDetectorCompat(context,this);
        gestureDetectorCompat.setOnDoubleTapListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        offsetX = (float)(getWidth() - bitmap.getWidth()) / 2;
        offsetY = (float) (getHeight() - bitmap.getHeight())/2;
        if((float)bitmap.getWidth() - bitmap.getHeight() > (float)(getWidth() - getHeight())){
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight();
        }else {
             bigScale = (float) getWidth() / bitmap.getWidth();
             smallScale = (float) getHeight() / bitmap.getHeight();
        }

    }

    public float getScaleFraction() {
        return scaleFraction;
    }

    public void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
    }

    public ObjectAnimator getScaleAnimator() {
        if(scaleAnimator == null){
            scaleAnimator = ObjectAnimator.ofFloat(this,"scaleFraction",0,1);
        }
        return scaleAnimator;
    }

    public void setScaleAnimator(ObjectAnimator scaleAnimator) {
        this.scaleAnimator = scaleAnimator;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        float scale = bigScale + (bigScale - smallScale) * scaleFraction;
//        canvas.scale(scale,scale,getWidth() / 2f ,getHeight() / 2f);

        canvas.drawBitmap(bitmap,offsetX,offsetY,paint);
//        canvas.drawBitmap(bitmap,0,0,paint);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // 预按下

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // 按下抬起

        return false;

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // onMove
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // 惯性滑动
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        big = !big;
        if(big){
            getScaleAnimator().start();
        }else {
            getScaleAnimator().reverse();
        }
        invalidate();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        //双击后滑动
        return false;
    }
}
