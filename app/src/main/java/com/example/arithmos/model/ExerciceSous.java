package com.example.arithmos.model;

import android.widget.ArrayAdapter;

import com.example.arithmos.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExerciceSous extends AbstractExercice {

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
        int res = 0, i = 0;
        List<Integer> listSous = new ArrayList<Integer>();
        while(i < modifiedTitle.length()) {
            if(modifiedTitle.charAt(i) == '#') {
                int min = 0;
                int max = 0;
                if (difficulty == 1) {
                    min = 1;
                    max = 9;
                } else if (difficulty == 2) {
                    min = 10;
                    max = 99;
                } else if (difficulty == 3){
                    min = 100;
                    max = 999;
                }
                int randomNumber = Utils.generateInteger(min, max);
                modifiedTitle.replace(i, i + 2, String.valueOf(randomNumber));
                listSous.add(randomNumber);
            }
            i++;
        }
        Collections.sort(listSous,Collections.reverseOrder());
        res = listSous.get(0);
        int deb = res;
        for(int k = 1; k < listSous.size(); k++){
            res -= listSous.get(k);
        }
        if ((deb - res) > 0){
            //error nombre negatif
        }

        q.setTitle(modifiedTitle.toString());
        q.setResult(res);

        return q;
    }
}
