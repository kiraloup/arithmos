package com.example.arithmos.question;

import java.util.ArrayList;

public abstract class InterfaceQuestion {
    protected int resultat;
    protected ArrayList<Integer> listValue;
    protected String title;

    public abstract Boolean checkValue(int response);
    public abstract ArrayList<Integer> generateValue(int nbValue, int min, int max);
    public abstract int generateResultat(int nbValues);

    public int getResultat() {
        return resultat;
    }

    public void setResultat(int resultat) {
        this.resultat = resultat;
    }

    public ArrayList<Integer> getListValue() {
        return listValue;
    }

    public void setListValue(ArrayList<Integer> listValue) {
        this.listValue = listValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
