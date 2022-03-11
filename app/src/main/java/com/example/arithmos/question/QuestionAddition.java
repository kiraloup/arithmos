package com.example.arithmos.question;

import com.example.arithmos.utils.Utils;

import java.util.ArrayList;

public class QuestionAddition extends InterfaceQuestion {


    public QuestionAddition() {
        this.listValue = generateValue(2, 0, 10);
        this.resultat = generateResultat(2);
        this.title = "Veuillez faire l'addition des nombre suivant";
    }


    @Override
    public Boolean checkValue(int response) {
        return response == resultat;
    }

    @Override
    public ArrayList<Integer> generateValue(int nbValue, int min, int max) {
        ArrayList<Integer> listValue = new ArrayList<>();
        for(int i = 0; i < nbValue; i++) {
            listValue.add(Utils.generateInteger(min, max));
        }
        return listValue;
    }

    @Override
    public int generateResultat(int nbValues) {
        int res = 0;
        for(int i = 0; i < nbValues; i++) {
            res += listValue.get(i);
        }
        return res;
    }
}
