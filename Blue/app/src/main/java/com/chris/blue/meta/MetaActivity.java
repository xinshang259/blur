package com.chris.blue.meta;

import android.car.CarMediaScannerManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chris.blue.R;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MetaActivity extends AppCompatActivity {

    private static final String TAG = "MetaActivity";

    private static final String URL = "https://image.so.com";
    private EditText editText;
    private MetaService metaService;

    private MetaRecyclerViewAdapter metaRecyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta);

        editText = findViewById(R.id.editText);

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                request();

            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(URL)
                .build();
        metaService = retrofit.create(MetaService.class);

        RecyclerView recyclerView = findViewById(R.id.meta_recyclerview);
        metaRecyclerViewAdapter = new MetaRecyclerViewAdapter(getApplicationContext(),
                null);
        recyclerView.setAdapter(metaRecyclerViewAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,RecyclerView.VERTICAL));

    }

    private void request() {
        Single.create(new SingleOnSubscribe<Object>() {

            @Override
            public void subscribe(SingleEmitter<Object> emitter) throws Exception {
                metaService.getImages(editText.getText().toString(),1,100).subscribe(new SingleObserver<List<ImageBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ImageBean> imageBeans) {
                        updateList(imageBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,e.toString());
                    }
                });
            }
        }).subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void updateList(List<ImageBean> imageBeans) {

        metaRecyclerViewAdapter.updateList(imageBeans);
    }
}
