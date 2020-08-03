package com.chris.blue.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.blue.R;

public class ScalableImageActivity extends AppCompatActivity {

//    private ActivityScalableBinding activityScalableBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activityScalableBinding = DataBindingUtil.setContentView(this, R.layout.activity_scalable);
//        activityScalableBinding.scalableView
        setContentView(R.layout.activity_scalable);

    }
}
