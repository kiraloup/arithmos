package com.example.arithmos.exercice;

import com.example.arithmos.question.InterfaceQuestion;
import com.example.arithmos.question.QuestionAddition;

import java.util.ArrayList;
import java.util.List;

//TODO:add is question finish, is exercices finish
public class Exercice {
    int numberOfErrors = 0;
    int NumberOfCurrentquestion = 0;
    List<InterfaceQuestion> listQuestion = new ArrayList<>();


    public void generateTypeOfExercice(String typeOfExercices, int numberOfQuestion) {
        if(typeOfExercices.equals("additions")) {
            generateAdditionQuestion();
        }
    }

    private void generateAdditionQuestion() {
        for(int i = 0; i < 10; i++) {
            listQuestion.add(new QuestionAddition());
        }
    }

    public InterfaceQuestion getNextQuestion() {
        NumberOfCurrentquestion++;
        return listQuestion.get(NumberOfCurrentquestion);
    }

    public Boolean isFinish() {
        return NumberOfCurrentquestion == 10;
    }

    public InterfaceQuestion getQuestion(){
        return listQuestion.get(NumberOfCurrentquestion);
    }
}
