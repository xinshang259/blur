package com.chris.blue;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.blue.activity.AnimationActivity;
import com.chris.blue.activity.ScalableImageActivity;
import com.ximalaya.speechcontrol.SpeechControler;
import com.ximalaya.speechcontrol.mediacontrol.IDataCallback;
import com.ximalaya.speechcontrol.mediacontrol.IPlayPauseListener;
import com.ximalaya.speechcontrol.mediacontrol.IServiceBindStatusCallBack;
import com.ximalaya.speechcontrol.mediacontrol.XmPlayerControler;
import com.ximalaya.ting.android.opensdk.model.PlayMode;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.PostResponse;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}