package com.example.arithmos.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRandom extends AbstractExercice{

    private final String TAG = "ExerciseRandom";

    public ExerciseRandom(int difficulty, TypeOfExercice typeOfExercice, int typeReponse) {
        super(difficulty, typeOfExercice, typeReponse);
    }

    public ExerciseRandom(int difficulty) {
        super(difficulty, TypeOfExercice.NUMBER, 1);
    }



    @Override
    public void createAllQuestion(List<Question> listQuestions, int difficulty) {
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAllQuestion(List<Question> listQuestions, String typeOfQuestion) {
        AbstractExercice exercice;

        ArrayList<Integer> table = new ArrayList<>();
        table.add(0);

        switch (typeOfQuestion) {
            case "add":
                Log.d(TAG, "ExerciseRandom ");
                exercice = new ExerciceAdd(difficulty, TypeOfExercice.NUMBER, 1, table);
                break;
            case "sous":
                exercice = new ExerciceSous(difficulty, TypeOfExercice.NUMBER, 1);
                break;
            case "mult":
                exercice = new ExerciceMult(difficulty, TypeOfExercice.NUMBER, 1, table);
                break;
            case "div":
                exercice = new ExerciceDiv(difficulty, TypeOfExercice.NUMBER, 1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeOfQuestion);
        }

        exercice.createAllQuestion(listQuestions, difficulty);

        this.listQuestion.addAll(exercice.getListQuestion());
    }
}
