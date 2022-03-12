package com.example.arithmos.model;

import java.util.ArrayList;
import java.util.List;

//TODO:add is question finish, is exercices finish
public abstract class AbstractExercice {
    int numberOfErrors = 0;
    int NumberOfCurrentquestion = 0;
    List<Question> listQuestion = new ArrayList<>();
    int difficulty;
    TypeOfExercice typeOfExercice;

    public AbstractExercice(int difficulty, TypeOfExercice typeOfExercice) {
        this.difficulty = difficulty;
        this.typeOfExercice = typeOfExercice;
    }

    public abstract void createAllQuestion(List<Question> listQuestions);

    public Question getNextQuestion() {
        NumberOfCurrentquestion++;
        return listQuestion.get(NumberOfCurrentquestion);
    }

    //TODO : check if - 1 doesn't create any error with list that have a size > 1
    public Boolean isFinish() {
        return NumberOfCurrentquestion == listQuestion.size() - 1;
    }

    public Question getQuestion(){
        return listQuestion.get(NumberOfCurrentquestion);
    }
}
