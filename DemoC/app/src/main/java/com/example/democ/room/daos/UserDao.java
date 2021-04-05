package com.example.democ.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.democ.room.entities.User;

@Dao
public interface UserDao {

    @Insert
    void addUser(User... user);

    @Query("DELETE FROM user")
    void deleteUser();

    @Query("SELECT * FROM user")
    LiveData<User> getUserInfo();

    @Query("SELECT * FROM user")
    User getmUserInfo();
}
