package com.chris.blue.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.blue.R;
import com.chris.blue.View.CircleView;
import com.chris.blue.utils.Util;

public class AnimationActivity  extends AppCompatActivity {

    private CircleView view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        view = findViewById(R.id.iv_head);
//        view.animate()
//                .translationY(Util.dp2Px(100))
//                .rotation(180)// 旋转
//                .setStartDelay(1000)
//                .start();

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"radius",Util.dp2Px(150));

        objectAnimator.setStartDelay(1000);
        objectAnimator.setDuration(500);
        objectAnimator.start();


    }
}
