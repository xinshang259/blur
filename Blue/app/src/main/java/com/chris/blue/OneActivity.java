package com.chris.blue;

import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chris.blue.adapter.OneAdapter;
import com.chris.blue.bean.User;
import com.chris.blue.databinding.ActivityOneBinding;
import com.chris.blue.utils.ThreadPoolManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OneActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "OneActivity";
    private CountDownLatch countDownLatch;
    private ActivityOneBinding binding;

    private OneViewModel oneViewModel;

    List<User> userList ;
    private OneAdapter oneAdapter;

    LiveData<PagedList<User>> allUserLiveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_one);
        oneViewModel = new ViewModelProvider(this).get(OneViewModel.class);
        oneAdapter = new OneAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this
                ,LinearLayoutManager.VERTICAL,false));
        binding.recyclerView.setAdapter(oneAdapter);
        binding.insert.setOnClickListener(this);
        binding.query.setOnClickListener(this);
        binding.delete.setOnClickListener(this);
        oneViewModel.init();
        binding.setData(oneViewModel);
        binding.setLifecycleOwner(this);
        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        oneViewModel.getListLiveData().observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(PagedList<User> users) {
                new ItemTouchHelper(new ItemTouchHelper.Callback() {
                    @Override
                    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                        return 0;
                    }

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    }
                }).attachToRecyclerView(binding.recyclerView);
            }
        });

    }


    private void threadOne() {
        ThreadPoolManager.getInstance().addDelayTask(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "2222222");
                countDownLatch.countDown();
            }
        }, 3, TimeUnit.SECONDS);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:
                break;
            case R.id.delete:
                oneViewModel.delete();
                break;
            case R.id.query:
                oneViewModel.query();
                break;
            case R.id.insert:
                oneViewModel.insert().subscribe();
                break;
        }

    }
}
