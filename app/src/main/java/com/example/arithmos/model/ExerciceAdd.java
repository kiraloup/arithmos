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
        int res = 0, i = 0;

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
}
