package com.example.arithmos.model;

import com.example.arithmos.utils.Utils;

import java.util.List;

public class ExerciceMult extends AbstractExercice {
    private final String TAG = "EXERCICEMULT";
    private int table;

    public ExerciceMult(int difficulty, TypeOfExercice typeOfExercice, int typeOfRep, int table) {
        super(difficulty, typeOfExercice, typeOfRep);
        this.table = table;
    }


    public ExerciceMult(int difficulty, TypeOfExercice typeOfExercice, int typeOfRep) {
        super(difficulty, typeOfExercice, typeOfRep);
        this.table = 0;
    }

    @Override
    public void createAllQuestion(List<Question> questions, int difficulty) {

        for(int i = 0; i < questions.size(); i++) {
                questions.set(i, createQuestionNumber(questions.get(i),difficulty));
        }

        this.listQuestion = questions;
    }

    private Question createQuestionNumber(Question q, int difficulty) {
        StringBuilder modifiedTitle = new StringBuilder(q.getTitle());
        int res = 1, i = 0, min = 0, max = 0;

        int[] range = getRange(difficulty);
        min = range[0];
        max = range[1];

        if (table == 0){
            while(i < modifiedTitle.length()) {
                if(modifiedTitle.charAt(i) == '#') {
                    int randomNumber = Utils.generateInteger(min, max);
                    res  = res * randomNumber;
                    modifiedTitle.replace(i, i + 2, String.valueOf(randomNumber));

                }
                i++;
            }
            q.setTitle(modifiedTitle.toString());
            q.setResult(res);
        } else {
            int valnb = 0;
            while(i < modifiedTitle.length()) {
                if(modifiedTitle.charAt(i) == '#') {
                    int randomNumber;
                    if (valnb == 0){
                        valnb ++;
                        randomNumber = table;
                    } else {
                        randomNumber = Utils.generateInteger(min, max);
                    }
                    res  = res * randomNumber;
                    modifiedTitle.replace(i, i + 2, String.valueOf(randomNumber));
                }
                i++;
            }
            q.setTitle(modifiedTitle.toString());
            q.setResult(res);
        }


        return q;
    }

    public int[] getRange(int difficulty) {
        int[] res = new int[2];
        if (difficulty == 1) {
            res[0] = 1;
            res[1] = 9;
        } else if (difficulty == 2) {
            res[0] = 1;
            res[1] = 99;
        } else if (difficulty == 3){
            res[0] = 1;
            res[1] = 200;
        }
        return res;
    }
}
