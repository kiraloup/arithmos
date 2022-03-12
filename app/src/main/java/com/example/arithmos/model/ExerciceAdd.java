package com.example.arithmos.model;

import com.example.arithmos.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ExerciceAdd extends AbstractExercice {

    @Override
    public void createAllQuestion(List<Question> questions) {
        this.listQuestion = questions;
        for(Question question : this.listQuestion) {
            createQuestion(question);
        }

    }

    private void createQuestion(Question q) {

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
    }
}
