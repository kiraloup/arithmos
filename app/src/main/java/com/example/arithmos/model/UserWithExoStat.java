package com.example.arithmos.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;


public class UserWithExoStat {

    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<ExoStat> exoStatList;
}
