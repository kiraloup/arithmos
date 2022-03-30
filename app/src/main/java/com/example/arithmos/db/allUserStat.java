package com.example.arithmos.db;

import androidx.room.ColumnInfo;

public class allUserStat {
    @ColumnInfo(name = "exo")
    public String exo;

    @ColumnInfo(name = "correct")
    public int correct;

    @ColumnInfo(name = "incorrect")
    public int incorrect;
}
