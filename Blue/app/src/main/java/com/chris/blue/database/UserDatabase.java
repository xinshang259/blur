package com.chris.blue.database;

import com.chris.blue.MyApplication;
import com.chris.blue.bean.User;


//@Database(entities = {User.class},version = 1,exportSchema = false)
//public abstract class UserDatabase extends RoomDatabase {
//
//    private static volatile UserDatabase instance;
//
//    public static UserDatabase getInstance(){
//        if(instance == null){
//            synchronized (UserDatabase.class){
//                if (instance == null) {
//                    instance = Room.databaseBuilder(MyApplication.getApplication(),UserDatabase.class,"user.db")
//                    .build();
//                }
//            }
//        }
//        return instance;
//    }
//
//
//    public abstract UserDao getUserDao();
//
//}
