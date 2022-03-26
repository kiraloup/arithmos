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
import com.example.arithmos.db.UserRepository;
import com.example.arithmos.model.AbstractExercice;
import com.example.arithmos.model.ExerciceAdd;
import com.example.arithmos.model.ExerciceDiv;
import com.example.arithmos.model.ExerciceMult;
import com.example.arithmos.model.ExerciceSous;
import com.example.arithmos.model.Question;
import com.example.arithmos.model.TypeOfExercice;
import com.example.arithmos.utils.Utils;

import java.util.ArrayList;
import java.util.List;

//TODO : Test the difference between number and string result
public class ExerciceViewModel extends AndroidViewModel {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    private AbstractExercice exercice;

    private String nameTypeOfExercise;

    //For debug only
    private final String TAG = "EXERCICEVIEWMODEL";


    //This is the object that the view is observing
    public MutableLiveData<Question> currentQuestion = new MutableLiveData<>();
    //boolean to check if exercice is finish while in the view
    public MutableLiveData<Boolean> isExerciceFinish = new MutableLiveData<>(false);
    //boolean to check if the loading ok otherwise we display a Toast message
    public MutableLiveData<Boolean> isLoadingOK = new MutableLiveData<>();

    public MutableLiveData<Boolean> isQuestionCorrect = new MutableLiveData<>();

    public ExerciceViewModel(@NonNull Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
        userRepository = new UserRepository(application);
    }

    /**
     *
     * @param typeOfExercice : if the exercise is type add, sub...
     * @param difficulty : exercise difficulty between 1 and 3
     * @param select : the response must be in number or letter
     * @param type :  exercise can be simple MCQ or in drag and drop style
     */
    public void createExercice(String typeOfExercice, int difficulty, int select, int type) {

        TypeOfExercice selectExercise = select == 1 ? TypeOfExercice.NUMBER : TypeOfExercice.LETTER;

        this.nameTypeOfExercise = typeOfExercice;

        switch (typeOfExercice) {
            case "add":
                exercice = new ExerciceAdd(difficulty, selectExercise);
                break;
            case "sous":
                exercice = new ExerciceSous(difficulty, selectExercise);
                break;
            case "mult":
                exercice = new ExerciceMult(difficulty, selectExercise);
                break;
            case "div":
                exercice = new ExerciceDiv(difficulty, selectExercise);
                break;
        }

        //we use a callback to create the exercice
        //because we need to access the database and it's not possible in UI thread
        questionRepository.getTenQuestionType(new RepositoryCallback<List<Question>>() {
            @Override
            public void onComplete(Result<List<Question>> result) {
                if(result instanceof Result.Success) {
                    List<Question> resData = ((Result.Success<List<Question>>) result).data;

                    exercice.createAllQuestion(resData, TypeOfExercice.NUMBER, difficulty);

                    Log.d(TAG, "SIZE OF Question : "
                            + String.valueOf(resData.size()));
                    Log.d(TAG, "TITLE Question 1 : "
                            + String.valueOf(resData.get(0).getTitle()));

                    currentQuestion.postValue(exercice.getQuestion());
                    isLoadingOK.postValue(true);
                } else if (result instanceof Result.Error){
                    //TODO : find a better way to handle error case
                    currentQuestion.postValue(new Question("ERROR", "ERROR", 2));
                    isLoadingOK.postValue(false);
                }
            }
        },typeOfExercice);

    }

    public void nextQuestion() {
        currentQuestion.setValue(exercice.getNextQuestion());
    }

    public boolean isExerciceFinish() {
        if(exercice.isFinish()) {
            Log.d(TAG, "Exercise is finish");

            int nbCorrectAnswer = exercice.getNumberOfQuestion() - exercice.getNumberOfError();
            //update the user profile with the number of good and wrong responses
            Log.d(TAG, "updating user stat");

            Log.d(TAG, "correct answer " + nbCorrectAnswer);
            Log.d(TAG, "wrong answer " + exercice.getNumberOfError());

            userRepository.setUserStat(this.nameTypeOfExercise, nbCorrectAnswer,
                    exercice.getNumberOfError());
            //the exercise is finish we tell the observer to change fragment
            //no need to put back to true since after we destroy the fragment
            isExerciceFinish.setValue(true);
            return true;
        }
        return false;
    }

    public Boolean checkResponse(String result) {
        if(exercice.getTypeOfExercice() == TypeOfExercice.LETTER) {
            String correctResponse = Utils.convertIntToStringMillier(exercice.getQuestion().getResult());
            Log.d(TAG, "Correct response  : "  + correctResponse);
            return correctResponse.equals(result);
        } else {
            int res = Integer.parseInt(result);
            return res == exercice.getQuestion().getResult();
        }
    }

    public void updateNumberOfError() {
        Log.d(TAG, "update number of error before : " + this.exercice.getNumberOfError());
        this.exercice.setNumberOfError(this.exercice.getNumberOfError() + 1);
        Log.d(TAG, "update number of error after : " + this.exercice.getNumberOfError());
    }

    public void setIsQuestionCorrect(Boolean b) {
        this.isQuestionCorrect.postValue(b);
    }

    public int[] getArrayOfImages() {
        //this is the value that are represented by image, 100 will be an apple with an x100
        int[] value = {1000,100, 50, 10, 1};
        //this is the number of image that can be display for each type of image
        int[] nvalue = {10, 10, 10, 10, 10};

        //the result to be broken
        if (!exercice.IsListQuestionEmpty()){
            int res = exercice.getQuestion().getResult();
            if( res <= 0) {
                return new int[]{};
            } else {
                return findNumberOfimage(value, nvalue, res);
            }
        }
        return new int[]{};
    }

    /**
     *
     * @param TypeOfimages should in ascending order since we want the lowest number of images
     * @param NumberOfimages should be a fix number of images for each type
     * @param value the actual value that need to be broken down
     * @return an array that contains how many image of each type do we need
     */
    private int[] findNumberOfimage(int[] TypeOfimages,int[] NumberOfimages,int value) {
        int i;
        int[] res = new int[NumberOfimages.length];

        for(i = 0; i < TypeOfimages.length; i++) {
            //TODO : rewrite this stupid comment
            //value >= TypeOfimages[i] is to check decremente the number of images
            //until the value is greater that the "coin" at place i
            while(value >= TypeOfimages[i] && NumberOfimages[i] > 0 && value >= 0) {
                //decremente the value since we select the image
                value -= TypeOfimages[i];
                Log.d(TAG, value + " is decremente by " + TypeOfimages[i]);

                // we check how many images of this type we can still display
                //we decremente this value
                NumberOfimages[i] = NumberOfimages[i] - 1;

                //now we incremente the result for this specific images
                //since we select it
                res[i]  = res[i] + 1;
            }
        }

        return res;
    }

}
