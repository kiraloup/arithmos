package com.example.arithmos.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.arithmos.model.User;
import com.example.arithmos.model.UserWithExoStat;

import java.util.List;
@Dao
public interface UserDao {
    @Insert void insertUser(User u);

    @Transaction
    @Query("SELECT * FROM user")
    List<UserWithExoStat> getUserWithExoStat();

    @Query("Delete from user")
    void deleteAll();
}
