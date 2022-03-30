package com.example.arithmos.model;

import com.example.arithmos.utils.Utils;

import java.util.List;

public class ExerciceDiv extends AbstractExercice {
    private final String TAG = "EXERCICEMULT";

    public ExerciceDiv(int difficulty, TypeOfExercice typeOfExercice,int typeOfRep) {
        super(difficulty, typeOfExercice, typeOfRep);
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
        int res = 0, i = 0, minFirst = 0, maxFirst = 0, minSecond = 0,maxSecond = 0;

        int[] rangeFirst = getRangeFirst(difficulty);
        minFirst = rangeFirst[0];
        maxFirst = rangeFirst[1];

        int[] rangeSecond = getRangeSecond(difficulty);
        minSecond = rangeSecond[0];
        maxSecond = rangeSecond[1];

        int nb = 0;
        while(i < modifiedTitle.length()) {
            if(modifiedTitle.charAt(i) == '#') {
                int randomNumber = 1;
                if (nb == 0) {
                    randomNumber = Utils.generateInteger(minFirst, maxFirst);
                    res += randomNumber;
                    nb++;
                } else {
                    randomNumber = Utils.generateInteger(minSecond, maxSecond);
                    while (res % randomNumber != 0){
                        randomNumber = Utils.generateInteger(minSecond, maxSecond);
                    }
                    res = res / randomNumber;
                    nb++;
                }


                modifiedTitle.replace(i, i + 2, String.valueOf(randomNumber));


            }
            i++;
        }
        q.setTitle(modifiedTitle.toString());
        q.setResult(res);

        return q;
    }

    public int[] getRangeFirst(int difficulty) {
        int[] res = new int[2];
        if (difficulty == 1) {
            res[0] = 2;
            res[1] = 9;
        } else if (difficulty == 2) {
            res[0] = 2;
            res[1] = 99;
        } else if (difficulty == 3){
            res[0] = 2;
            res[1] = 999;
        }
        return res;
    }

    public int[] getRangeSecond(int difficulty) {
        int[] res = new int[2];
        if (difficulty == 1) {
            res[0] = 2;
            res[1] = 9;
        } else if (difficulty == 2) {
            res[0] = 2;
            res[1] = 10;
        } else if (difficulty == 3){
            res[0] = 2;
            res[1] = 100;
        }
        return res;
    }
}
