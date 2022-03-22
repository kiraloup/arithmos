package com.example.arithmos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Exo_Stat")
public class ExoStat {

    @NonNull
    @ColumnInfo(name = "userId")
    private int userId;

    @NonNull
    @ColumnInfo(name = "exo")
    @PrimaryKey
    private String idExo;

    @ColumnInfo(name = "correct")
    private int nbOk;

    @ColumnInfo(name = "incorrect")
    private int nbErreur;

    @ColumnInfo(name = "pourcentage")
    private int pourcentage;

    public ExoStat(int userId, @NonNull String idExo, int nbOk, int nbErreur, int pourcentage) {
        this.userId = userId;
        this.idExo = idExo;
        this.nbOk = nbOk;
        this.nbErreur = nbErreur;
        this.pourcentage = pourcentage;
    }

    @NonNull
    public String getIdExo() {
        return idExo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setIdExo(@NonNull String idExo) {
        this.idExo = idExo;
    }

    public int getNbOk() {
        return nbOk;
    }

    public void setNbOk(int nbOk) {
        this.nbOk = nbOk;
    }

    public int getNbErreur() {
        return nbErreur;
    }

    public void setNbErreur(int nbErreur) {
        this.nbErreur = nbErreur;
    }

    public int getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(int pourcentage) {
        this.pourcentage = pourcentage;
    }
}


