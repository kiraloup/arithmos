package com.example.arithmos.model;

import com.example.arithmos.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ExerciceAdd extends AbstractExercice {

    public ExerciceAdd(int difficulty, TypeOfExercice typeOfExercice) {
        super(difficulty, typeOfExercice);
    }

    @Override
    public void createAllQuestion(List<Question> questions) {
        this.listQuestion = questions;
        for(Question question : this.listQuestion) {
            if(typeOfExercice == TypeOfExercice.NUMBER) {
                createQuestionNumber(question);
            } else {
                createQuestionString(question);
            }
        }

    }

    private void createQuestionNumber(Question q) {

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

    private void createQuestionString(Question q) {

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
        q.setResult(convertIntToString(res));

    }

    private String convertIntToString(int num) {
        String[] dizaine = {"vingt", "trente", "quarante", "cinquante", "soixante",
                "soixante-dix", "quatre-vingt", "quatre-vint-dix"};

        String[] nombre = {"zero", "un", "deux", "trois", "quatre", "cinq", "six",
                "sept", "huit", "neuf", "dix", "onze", "douze", "treize", "quatorze", "quinze",
                "seize", "dix-sept", "dix-huit", "dix-neuf"};

        String dcap = "";
        if(num < 20) {
            return dcap.concat(nombre[num]);
        }
        else {
            for (int v = 0; v < dizaine.length; v++) {
                dcap = dizaine[v];
                int dval = 20 + 10 * v;
                if (dval + 10 > num) {
                    if ((num % 10) != 0)
                        return dcap + "-" + nombre[num % 10];
                    return dcap;
                }
            }
        }
        return dcap;
    }
}
