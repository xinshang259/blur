package com.chris.blue.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chris.blue.MainActivity;
import com.chris.blue.R;
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

public class JsUsbTestActivity extends AppCompatActivity {

    private SpeechControler controler;
    private TextView modelName;
    private String TAG = "MainActivity";

    private XmPlayerControler mInstance;
    private TextView mTvSearchResult;
    private EditText mEtNlu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelName = (TextView) findViewById(R.id.modelname);
        mTvSearchResult = (TextView) findViewById(R.id.tv_search_result);
        mEtNlu = (EditText) findViewById(R.id.et_nlu);
        controler = SpeechControler.getInstance();
        controler.init(this.getApplicationContext(), new IServiceBindStatusCallBack() {
            @Override
            public void success() {
                Toast.makeText(JsUsbTestActivity.this, "播放器层 连接了", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failed() {
                Toast.makeText(JsUsbTestActivity.this, "播放器层 服务挂了！请重新初始化，并连接！", Toast.LENGTH_LONG)
                        .show();

            }
        });

        mInstance = XmPlayerControler.getInstance();
        mInstance.init(this.getApplicationContext(), new IServiceBindStatusCallBack() {
            @Override
            public void success() {
                controler.addPlayerStatusListener(mXmPlayerStatusListener);
                Toast.makeText(JsUsbTestActivity.this, "业务层 连接了", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failed() {
                Toast.makeText(JsUsbTestActivity.this, "业务层 服务挂了！请重新初始化，并连接！", Toast.LENGTH_LONG).show();
            }
        });

        mInstance.setPlayPauseListener(new IPlayPauseListener() {
            @Override
            public void playPause(int i) {
                Toast.makeText(JsUsbTestActivity.this, "play pause because of pay", Toast.LENGTH_SHORT)
                        .show();
                Log.e(TAG, "playPause: ");
            }
        });

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JsUsbTestActivity.this, ScalableImageActivity.class));
            }
        });
    }

    public void myButtonClick(View view) {
        final String word = mEtNlu.getText().toString().trim();
        switch (view.getId()) {
            case R.id.playpre:
                boolean result = controler.playPre();
                Log.e(TAG, "playpre result: " + result);
                break;
            case R.id.playnext:
                boolean result2 = controler.playNext();
                Log.e(TAG, "playnext result: " + result2);
                break;
            case R.id.play:
                controler.play();
                break;
            case R.id.pause:
                controler.pause();
                break;
            case R.id.btn_play_mode:
                controler.setPlayMode(PlayMode.PLAY_MODEL_RANDOM.toString());
                break;
            case R.id.collect:
                mInstance.collectOrUncollect(true, new IDataCallback<PostResponse>() {
                    @Override
                    public void success(PostResponse obj) {
                        modelName.setText("收藏成功");
                        Log.e(TAG, "collectOrUncollect success: " + obj.getMessage());
                    }

                    @Override
                    public void error(int code, String msg) {
                        Log.e(TAG, "collectOrUncollect error: " + msg);
                        modelName.setText(msg);
                    }
                });
                break;
            case R.id.uncollect:
                mInstance.collectOrUncollect(false, new IDataCallback<PostResponse>() {
                    @Override
                    public void success(PostResponse obj) {
                        Log.e(TAG, "collectOrUncollect success: " + obj.getMessage());
                        modelName.setText("取消收藏成功");
                    }

                    @Override
                    public void error(int code, String msg) {
                        Log.e(TAG, "collectOrUncollect error: " + msg);
                        modelName.setText(msg);
                    }
                });
                break;
            case R.id.btn_open_continue_play:
                controler.openAppAndContinuePlay(this);
                break;
            case R.id.gotoHome: {
                //启动应用、进入首页
                try {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse("itingwelcom://open"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            case R.id.btn_exit:
                controler.exitApp();
                break;
            case R.id.getCurrTrack:
                Track track = controler.getCurrentTrack();
                if (track != null) {
                    modelName.setText(track.getTrackTitle());
                    Log.d(TAG,"track.getTrackTitle()) : "+track.getTrackTitle());
                } else {
                    Log.d(TAG,"track.getTrackTitle()) : null");
                }
                break;
            case R.id.btn_check:
                if (TextUtils.isEmpty(word)) {
                    Toast.makeText(this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                }
                mInstance.setPlayKeyword(word, 0, new IDataCallback<String>() {
                    @Override
                    public void success(String o) {
                        String msg = "";
                        msg = o;
                        mTvSearchResult.setText("成功播放或播控：" + word + msg);
                        Log.e(TAG, "getNLUSearch success: " + word);
                    }

                    @Override
                    public void error(int i, String s) {
                        mTvSearchResult.setText("结果：" + s);
                        Log.e(TAG, "getNLUSearch error: " + s);
                    }
                });
                break;
            case R.id.btn_play_album:
                if (TextUtils.isEmpty(word)) {
                    Toast.makeText(this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                }
                mInstance.setPlayKeyword(word, 1, new IDataCallback<String>() {
                    @Override
                    public void success(String o) {
                        String msg = "";
                        msg = o;
                        mTvSearchResult.setText("成功播放或播控：" + word + msg);
                        Log.e(TAG, "getNLUSearch success: " + word);
                    }

                    @Override
                    public void error(int i, String s) {
                        mTvSearchResult.setText("结果：" + s);
                        Log.e(TAG, "getNLUSearch error: " + s);
                    }
                });
                break;

            case R.id.btn_play_track:
                if (TextUtils.isEmpty(word)) {
                    Toast.makeText(this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                }
                mInstance.setPlayKeyword(word, 2, new IDataCallback<String>() {
                    @Override
                    public void success(String o) {
                        String msg = "";
                        msg = o;
                        mTvSearchResult.setText("成功播放或播控：" + word + msg);
                        Log.e(TAG, "getNLUSearch success: " + word);
                    }

                    @Override
                    public void error(int i, String s) {
                        mTvSearchResult.setText("结果：" + s);
                        Log.e(TAG, "getNLUSearch error: " + s);
                    }
                });
                break;
            case R.id.btn_play_radio:
                if (TextUtils.isEmpty(word)) {
                    Toast.makeText(this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                }
                mInstance.setPlayKeyword(word, 3, new IDataCallback<String>() {
                    @Override
                    public void success(String o) {
                        String msg = "";
                        msg = o;
                        mTvSearchResult.setText("成功播放或播控：" + word + msg);
                        Log.e(TAG, "getNLUSearch success: " + word);
                    }

                    @Override
                    public void error(int i, String s) {
                        mTvSearchResult.setText("结果：" + s);
                        Log.e(TAG, "getNLUSearch error: " + s);
                    }
                });
                break;
            case R.id.btn_play_live:
                if (TextUtils.isEmpty(word)) {
                    Toast.makeText(this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                }
                mInstance.setPlayKeyword(word, 4, new IDataCallback<String>() {
                    @Override
                    public void success(String o) {
                        String msg = "";
                        msg = o;
                        mTvSearchResult.setText("成功播放或播控：" + word + msg);
                        Log.e(TAG, "getNLUSearch success: " + word);
                    }

                    @Override
                    public void error(int i, String s) {
                        mTvSearchResult.setText("结果：" + s);
                        Log.e(TAG, "getNLUSearch error: " + s);
                    }
                });
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        if (controler != null) {
            controler.removePlayerStatusListener(mXmPlayerStatusListener);
            controler.destroy();
        }
        if (mInstance != null) {
            mInstance.onDestroy();
        }

        super.onDestroy();
    }

    private IXmPlayerStatusListener mXmPlayerStatusListener = new IXmPlayerStatusListener() {
        @Override
        public void onSoundSwitch(PlayableModel lastModel, PlayableModel curModel) {
            // TODO Auto-generated method stub
            Log.e(TAG, "onSoundSwitch");
            getCurrentInfo();
            modelName.setText("onSoundSwitch");
        }

        @Override
        public void onSoundPrepared() {
            // TODO Auto-generated method stub
            modelName.setText("onSoundPrepared");
            Log.e(TAG, "onSoundPrepared: ");

        }

        @Override
        public void onSoundPlayComplete() {
            // TODO Auto-generated method stub
            modelName.setText("onSoundPlayComplete");
            Log.e(TAG, "onSoundPlayComplete: ");

        }

        @Override
        public void onPlayStop() {
            // TODO Auto-generated method stub
            modelName.setText("onPlayStop");
            Log.e(TAG, "onPlayStop: ");

        }

        @Override
        public void onPlayStart() {
            // TODO Auto-generated method stub
            modelName.setText("onPlayStart");
            Log.e(TAG, "onPlayStart: ");
            getCurrentInfo();
        }

        @Override
        public void onPlayProgress(int currPos, int duration) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPlayPause() {
            // TODO Auto-generated method stub

            Log.e(TAG, "onPlayPause");
            modelName.setText("onPlayPause");
        }


        @Override
        public boolean onError(XmPlayerException exception) {
            // TODO Auto-generated method stub
            modelName.setText("onError");
            Log.e(TAG, "onError: " + exception.getMessage());
            Log.e(TAG, "onError: " + exception.getLocalizedMessage());
            exception.printStackTrace();
            Log.e(TAG, "onError: msg end");

            return false;
        }

        @Override
        public void onBufferingStop() {
            // TODO Auto-generated method stub
            modelName.setText("onBufferingStop");
            Log.e(TAG, "onBufferingStop");

        }

        @Override
        public void onBufferingStart() {
            // TODO Auto-generated method stub
            Log.e(TAG, "onBufferingStart");

            modelName.setText("onBufferingStart");
        }

        @Override
        public void onBufferProgress(int percent) {
            // TODO Auto-generated method stub

        }
    };

    private void getCurrentInfo() {
        Track currentTrack = controler.getCurrentTrack();
        Log.e(TAG, "getCurrentInfo: " + currentTrack);
        if (currentTrack != null) {
            String title = currentTrack.getTrackTitle();
            String urlLarge = currentTrack.getCoverUrlLarge();
        }

    }
}
