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

        String modifiedTitle = q.getTitle();
        int res = 0;

        for(int i = 0; i < q.getExpectedNumValues(); i++) {
            //on replace le paramètre par un nombre dans
            int genNum = Utils.generateInteger(1, 10);
            res += genNum;
            modifiedTitle = modifiedTitle.replace("#" + i,
                    String.valueOf(genNum));
        }

        q.setTitle(modifiedTitle);
        q.setResult(res);
    }
}
