package com.example.arithmos.model;

import java.util.ArrayList;
import java.util.List;

//TODO:add is question finish, is exercices finish
public abstract class AbstractExercice {
    int numberOfErrors = 0;
    int NumberOfCurrentquestion = 0;
    List<Question> listQuestion = new ArrayList<>();

    public abstract void createAllQuestion(List<Question> listQuestions);

    public Question getNextQuestion() {
        NumberOfCurrentquestion++;
        return listQuestion.get(NumberOfCurrentquestion);
    }

    public Boolean isFinish() {
        return NumberOfCurrentquestion == 10;
    }

    public Question getQuestion(){
        return listQuestion.get(NumberOfCurrentquestion);
    }
}
