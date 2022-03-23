package com.example.arithmos.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.arithmos.model.ExoStat;
import com.example.arithmos.model.User;
import com.example.arithmos.model.UserWithExoStat;

import java.util.List;
@Dao
public interface UserDao {
    @Insert void insertUser(User u);

    @Insert void inserAllExosStat(List<ExoStat> exoStatList);

    default void insertUserStat(User e, List<ExoStat> statList) {
        for (ExoStat exo: statList) {
            exo.setUserId(e.getId());
        }

        inserAllExosStat(statList);
    }


    @Query("SELECT * FROM Exo_Stat where userId = 0")
    List<ExoStat> getUserWithExoStat();

    @Query("Delete from user")
    void deleteAll();
}
