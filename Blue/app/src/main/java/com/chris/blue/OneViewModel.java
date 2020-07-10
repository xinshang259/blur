package com.chris.blue;

import android.app.Application;

import com.chris.blue.bean.User;
import com.chris.blue.database.UserDao;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class OneViewModel extends AndroidViewModel {

    private static final int ONE_SECOND = 1000;

    private MutableLiveData<List<User>> userLiveData = new MutableLiveData<>();

    private long mInitialTime;



    private Application application;
    private UserDao userDao;
    public MutableLiveData<String> nameLiveData = new MutableLiveData<>();
    public String name;
    public LiveData<List<User>> userData;

    private LiveData<PagedList<User>> listLiveData;

    private String text = "";

    public OneViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

    }

    public LiveData<PagedList<User>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<List<User>> getUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(List<User> userList){
        userLiveData.setValue(userList);
    }

    public void setUser(User user){
        nameLiveData.setValue(user.toString());
    }

    public LiveData<List<User>> getUserData() {
        return userData;
    }

    public void query(){
        LiveData<List<User>> users = userDao.getUsers();
//        for (User user : userList) {
//            nameLiveData.setValue(user.toString());
//        }
//        SingletonDemo.Companion singletonDemo = SingletonDemo.Companion;
    }

    public Observable insert(User... users){
        return Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> users) throws Exception {
                for (int i =0 ;i<10;i++ ){
                    User user = new User("张三",20);
                    userDao.insertUsers(user);
                }
                query();
            }

        });

    }

    public void delete(){
        userDao.deleteUsers();
        query();
    }

    public void update(){
//        List<User> userList = userDao.getUsers();

//        for (User user : userList) {
//            int count = 0;
//            count ++;
//            name += count +" name : "+user.getName()+" age : "+user.getAge()+"\n";
//            nameLiveData.setValue(name);
//        }

    }

    public void init() {

//        UserDatabase userDatabase = Room.databaseBuilder(application,UserDatabase.class,"user_database.db")
//                .allowMainThreadQueries()
//
//                .build();
//        userDao = userDatabase.getUserDao();

        DataSource.Factory<Integer, User> myConcertDataSource =
                userDao.getUsersForPaging();

        listLiveData = new LivePagedListBuilder<>(myConcertDataSource, 50).build();

    }
}
