package com.example.arithmos.model;

import com.example.arithmos.utils.Utils;

import java.util.List;

public class ExerciceAdd extends AbstractExercice {

    public ExerciceAdd(int difficulty, TypeOfExercice typeOfExercice) {
        super(difficulty, typeOfExercice);
    }


    @Override
    public void createAllQuestion(List<Question> questions,
                                            TypeOfExercice typeOfExercice,int difficulty) {

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

        int[] range = getRange(difficulty);
        min = range[0];
        max = range[1];

        while(i < modifiedTitle.length()) {
            if(modifiedTitle.charAt(i) == '#') {
                int randomNumber = Utils.generateInteger(min, max);

                res += randomNumber;
                int nb = 0;
                boolean flag = false;

                while (res > 999) {
                    flag = true;
                    nb ++;
                    res -= (randomNumber/2);
                }
                if (flag) {
                    modifiedTitle.replace(i, i + 2, String.valueOf(randomNumber/(2*nb)));
                } else {
                    modifiedTitle.replace(i, i + 2, String.valueOf(randomNumber));
                }
                flag = false;

            }
            i++;
        }
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
