package com.example.arithmos.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String profilType; // 1 - novice / 2 - intermédiaire / 3 - expert
    private int tauxInteractivite;


    public User(String nom, String prenom, int age, String profilType, int tauxInteractivite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.profilType = profilType;
        this.tauxInteractivite = tauxInteractivite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfilType() {
        return profilType;
    }

    public void setProfilType(String profilType) {
        this.profilType = profilType;
    }

    public int getTauxInteractivite() {
        return tauxInteractivite;
    }

    public void setTauxInteractivite(int tauxInteractivite) {
        this.tauxInteractivite = tauxInteractivite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
