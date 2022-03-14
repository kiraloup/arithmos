package com.example.arithmos.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.arithmos.db.QuestionRepository;
import com.example.arithmos.db.RepositoryCallback;
import com.example.arithmos.db.Result;
import com.example.arithmos.model.AbstractExercice;
import com.example.arithmos.model.ExerciceAdd;
import com.example.arithmos.model.Question;
import com.example.arithmos.model.TypeOfExercice;
import com.example.arithmos.utils.Utils;

import java.util.ArrayList;
import java.util.List;

//TODO : Test the difference between number and string result
public class ExerciceViewModel extends AndroidViewModel {

    private final QuestionRepository questionRepository;

    private AbstractExercice exercice;

    //For debug only
    private final String TAG = "EXERCICEVIEWMODEL";


    //This is the object that the view is observing
    public MutableLiveData<Question> currentQuestion = new MutableLiveData<>();
    //boolean to check if exercice is finish while in the view
    public MutableLiveData<Boolean> isExerciceFinish = new MutableLiveData<>(false);

    public ExerciceViewModel(@NonNull Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
    }

    /**
     *
     * @param typeOfExercice : if the exercise is type add, sub...
     * @param difficulty : exercise difficulty between 1 and 3
     * @param select : exercise can be simple MCQ or in drag and drop style
     * @param type : the response must be in number or letter
     */
    public void createExercice(String typeOfExercice, int difficulty, int select, int type) {


        TypeOfExercice selectExercise = type == 1 ? TypeOfExercice.NUMBER : TypeOfExercice.LETTER;

        if(typeOfExercice.equals("add")) {

            exercice = new ExerciceAdd(difficulty, selectExercise);

            //we use a callback to create the exercice
            //because we need to access the database and it's not possible in UI thread
            questionRepository.getAllQuestion(new RepositoryCallback<List<Question>>() {
                @Override
                public void onComplete(Result<List<Question>> result) {
                    if(result instanceof Result.Success) {
                        List<Question> resData = ((Result.Success<List<Question>>) result).data;

                        exercice.createAllQuestion(resData, TypeOfExercice.NUMBER);

                        Log.d(TAG, "SIZE OF Question : "
                                + String.valueOf(resData.size()));
                        Log.d(TAG, "TITLE Question 1 : "
                                + String.valueOf(resData.get(0).getTitle()));

                        currentQuestion.postValue(exercice.getQuestion());
                    } else if (result instanceof Result.Error){
                        //TODO : find a better way to handle error case
                        currentQuestion.postValue(new Question("ERROR", "ERROR", 2));
                    }
                }
            });
        }
    }

    public void nextQuestion() {
        currentQuestion.setValue(exercice.getNextQuestion());
    }

    public boolean isExerciceFinish() {
        if(exercice.isFinish()) {
            Log.d(TAG, "Exercice is finish");
            isExerciceFinish.setValue(true);
            return true;
        }
        return false;
    }

    public Boolean checkResponse(String result) {
        int res = Integer.parseInt(result);

        if(exercice.getTypeOfExercice() == TypeOfExercice.LETTER) {

            String correctResponse = Utils.convertIntToString(exercice.getQuestion().getResult());

            //TODO : handle edge cases like number > 1000 and else
            String userResponse = Utils.convertIntToString(res);

            return correctResponse.equals(userResponse);
        } else {
            return res == exercice.getQuestion().getResult();
        }
    }
}
