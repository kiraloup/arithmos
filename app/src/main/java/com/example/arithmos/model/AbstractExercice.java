package com.example.arithmos.model;

import java.util.ArrayList;
import java.util.List;

//TODO:add is question finish, is exercices finish
public abstract class AbstractExercice {

    int currentQuestion = 0;
    int numberOfError = 0;
    List<Question> listQuestion = new ArrayList<>();
    int difficulty;
    TypeOfExercice typeOfExercice;
    int typeReponse;

    public AbstractExercice(int difficulty, TypeOfExercice typeOfExercice,  int typeReponse) {
        this.difficulty = difficulty;
        this.typeOfExercice = typeOfExercice;
        this.typeReponse = typeReponse;
    }

    public abstract void  createAllQuestion(List<Question> listQuestions,
                                                     TypeOfExercice typeOfExercice,int difficulty);

    public Question getNextQuestion() {
        currentQuestion++;
        return listQuestion.get(currentQuestion);
    }

    public void setTypeOfExercice(TypeOfExercice typeOfExercice) {
        this.typeOfExercice = typeOfExercice;
    }

    //TODO : check if - 1 doesn't create any error with list that have a size > 1
    public Boolean isFinish() {
        return currentQuestion == listQuestion.size() - 1;
    }

    public Question getQuestion(){
        return listQuestion.get(currentQuestion);
    }

    public boolean IsListQuestionEmpty(){
        return listQuestion.isEmpty();
    }

    public TypeOfExercice getTypeOfExercice() {
        return typeOfExercice;
    }

    public int getNumberOfQuestion() {
        return listQuestion.size();
    }

    public int getNumberOfError() {
        return numberOfError;
    }

    public int getTypeReponse() {
        return typeReponse;
    }

    public void setTypeReponse(int typeReponse) {
        this.typeReponse = typeReponse;
    }



    public void setNumberOfError(int numberOfError) {
        this.numberOfError = numberOfError;
    }
}
