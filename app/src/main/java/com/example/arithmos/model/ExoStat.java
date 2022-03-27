package com.example.arithmos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Exo_Stat", primaryKeys = {"exo","typeReponse"})
public class ExoStat {

    @NonNull
    @ColumnInfo(name = "userId")
    private int userId;

    @NonNull
    @ColumnInfo(name = "exo")
    private String idExo;

    @ColumnInfo(name = "correct")
    private int nbOk;

    @ColumnInfo(name = "incorrect")
    private int nbErreur;

    @ColumnInfo(name = "pourcentage")
    private float pourcentage;

    // 0 = num exo simple / 1 = lettre exo simple // 2 = drag and drop
    @ColumnInfo(name = "typeReponse")
    private int typeReponse;



    public ExoStat(int userId, @NonNull String idExo, int nbOk, int nbErreur, float pourcentage, int typeReponse) {
        this.userId = userId;
        this.idExo = idExo;
        this.nbOk = nbOk;
        this.nbErreur = nbErreur;
        this.pourcentage = pourcentage;
        this.typeReponse = typeReponse;

    }



    @NonNull
    public String getIdExo() {
        return idExo;
    }

    public int getTypeReponse() {
        return typeReponse;
    }

    public void setTypeReponse(int typeReponse) {
        this.typeReponse = typeReponse;
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

    public float getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(float pourcentage) {
        this.pourcentage = pourcentage;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}


