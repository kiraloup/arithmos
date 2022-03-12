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

import java.util.ArrayList;
import java.util.List;

//TODO : Connect it to the view
public class ExerciceViewModel extends AndroidViewModel {

    private final QuestionRepository questionRepository;

    private AbstractExercice exercice;

    //For debug only
    private final String TAG = "EXERCICEVIEWMODEL";

    private static int PASSAGE = 0;

    //This is the object that the view is observing
    public MutableLiveData<Question> currentQuestion = new MutableLiveData<>();
    //boolean to check if exercice is finish while in the view
    public MutableLiveData<Boolean> isExerciceFinish = new MutableLiveData<>(false);

    public ExerciceViewModel(@NonNull Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
    }

    public void createExercice(String typeOfExercice) {
        PASSAGE++;

        if(typeOfExercice.equals("addition")) {
            exercice = new ExerciceAdd(1, TypeOfExercice.NUMBER);

            Log.d(TAG, String.valueOf(PASSAGE));
            //we use a callback to create the exercice
            //because we need to access the database and it's not possible in UI thread
            questionRepository.getAllQuestion(new RepositoryCallback<List<Question>>() {
                @Override
                public void onComplete(Result<List<Question>> result) {
                    if(result instanceof Result.Success) {
                        List<Question> resData = ((Result.Success<List<Question>>) result).data;
                        Log.d(TAG, "SIZE OF Question : " + String.valueOf(resData.size()));
                        Log.d(TAG, "TITLE Question 1 : " + String.valueOf(resData.get(0).getTitle()));
                        exercice.createAllQuestion(resData);

                        Log.d(TAG, "TITLE Question 1 title : " + exercice.getQuestion().getTitle());
                        Log.d(TAG, "TITLE Question 1 res : " + exercice.getQuestion().getResult());
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

    public Boolean isResponseCorrect(int reponse) {
        return exercice.getQuestion().getResult() == reponse;
    }
}
