package com.example.arithmos.model;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.arithmos.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExerciceSous extends AbstractExercice {

    private final String TAG = "EXERCICESOUS";

    public ExerciceSous(int difficulty, TypeOfExercice typeOfExercice) {
        super(difficulty, typeOfExercice);
    }


    @Override
    public void createAllQuestion(List<Question> questions,
                                  TypeOfExercice typeOfExercice, int difficulty) {

        for(int i = 0; i < questions.size(); i++) {
            if(typeOfExercice == TypeOfExercice.NUMBER) {
                questions.set(i, createQuestionNumber(questions.get(i),difficulty));
            } /*else {
                createQuestionString(question);
            }*/
        }

        this.listQuestion = questions;
    }


    private Question createQuestionNumber(Question q, int difficulty) {

        StringBuilder modifiedTitle = new StringBuilder(q.getTitle());
        int res = 0, i = 0, min = 0, max = 0;
        List<Integer> listSous = new ArrayList<Integer>();
        //list position des #
        List<Integer> listdol = new ArrayList<Integer>();

        int[] range = getRange(difficulty);
        min = range[0];
        max = range[1];

        while(i < modifiedTitle.length()) {
            if(modifiedTitle.charAt(i) == '#') {
                int randomNumber = Utils.generateInteger(min, max);
                res += randomNumber;
                listdol.add(i);
                listSous.add(randomNumber);
            }
            i++;
        }

        System.out.println("sizeoflist" + listdol.size());
        //Collections.sort(listSous);
        Collections.reverse(listSous);
        res = listSous.get(0);
        System.out.println("res : " + listSous.get(0)+ "listdolres"+listdol.get(0)+ "autre " + listSous.get(1)+" listdol "+ listdol.get(1));

        for (int j = 1 ; j < listdol.size(); j++) {
            res -= listSous.get(j);
        }

        while (res < 0){
            res = listSous.get(0);
            for (int k = 1; k < listSous.size(); k++){
                System.out.println(" On retire car gauche = "+ listSous.get(0)+" et droite ="  + listSous.get(k));
                listSous.set(k,(listSous.get(k)/2));
                res -= listSous.get(k);
            }
        }

        int indexRVal = 0;
        for (i = 0 ; i < modifiedTitle.length(); i++) {
            if(modifiedTitle.charAt(i) == '#') {
                modifiedTitle.replace(i, i + 2, String.valueOf(listSous.get(indexRVal)));
                indexRVal++;
            }
        }

        Log.d(TAG, "Expected answer" + res);
        q.setTitle(modifiedTitle.toString());
        q.setResult(res);

        return q;
    }

    public int[] getRange(int difficulty) {
        int[] res = new int[2];
        if (difficulty == 1) {
            res[0] = 1;
            res[1] = 9;
        } else if (difficulty == 2) {
            res[0] = 10;
            res[1] = 99;
        } else if (difficulty == 3){
            res[0] = 100;
            res[1] = 999;
        }
        return res;
    }
}

