package com.chris.blue.database;

import com.chris.blue.bean.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

//@Dao
public interface UserDao  {

//    @Query("SELECT * FROM User ORDER BY id")
    LiveData<List<User>> getUsers();

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(User... users);

//    @Update
    void updateUsers(User... users);

//    @Query("DELETE FROM User")
    void deleteUsers();

//    @Query("DELETE FROM User")
    DataSource.Factory<Integer,User> getUsersForPaging();


}
