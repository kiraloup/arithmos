package com.example.arithmos.model;

import com.example.arithmos.utils.Utils;

import java.util.List;

public class ExerciceAdd extends AbstractExercice {

    public ExerciceAdd(int difficulty, TypeOfExercice typeOfExercice) {
        super(difficulty, typeOfExercice);
    }


    @Override
    public void createAllQuestion(List<Question> questions,
                                            TypeOfExercice typeOfExercice) {

        for(int i = 0; i < questions.size(); i++) {
            if(typeOfExercice == TypeOfExercice.NUMBER) {
                questions.set(i, createQuestionNumber(questions.get(i)));
            }
        }

        this.listQuestion = questions;
    }

    private Question createQuestionNumber(Question q) {

        StringBuilder modifiedTitle = new StringBuilder(q.getTitle());
        int res = 0, i = 0;

        while(i < modifiedTitle.length()) {
            if(modifiedTitle.charAt(i) == '#') {
                int randomNumber = Utils.generateInteger(0, 10);
                modifiedTitle.replace(i, i + 2, String.valueOf(randomNumber));
                res += randomNumber;
            }
            i++;
        }

        q.setTitle(modifiedTitle.toString());
        q.setResult(res);

        return q;
    }
}
