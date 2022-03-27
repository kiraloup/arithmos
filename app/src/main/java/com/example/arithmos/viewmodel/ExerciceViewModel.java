package com.example.arithmos.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
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

import java.util.List;


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
    //boolean trigger when the user click on the button
    //we check user response to the current question in the observer
    public MutableLiveData<Boolean> checkCurrentQuestion = new MutableLiveData<>(false);
    //boolean to check if the loading ok otherwise we display a Toast message
    public MutableLiveData<Boolean> isLoadingOK = new MutableLiveData<>();
    //is user result ok
    public MutableLiveData<Boolean> isResponseCorrect = new MutableLiveData<>();


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

        // 0 = simple rep num / 1 = simple rep lettre / 2 = drag and drop
        int typeOfRes = 1;
        if (type == 1){
            if (select == 1) {
                typeOfRes = 0;
            } else {
                typeOfRes = 1;
            }
        } else {
            typeOfRes = 3;
        }

        switch (typeOfExercice) {
            case "add":
                exercice = new ExerciceAdd(difficulty, selectExercise, typeOfRes);
                break;
            case "sous":
                exercice = new ExerciceSous(difficulty, selectExercise, typeOfRes);
                break;
            case "mult":
                exercice = new ExerciceMult(difficulty, selectExercise, typeOfRes);
                break;
            case "div":
                exercice = new ExerciceDiv(difficulty, selectExercise, typeOfRes);
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

    public boolean isExerciseFinish() {
        if(exercice.isFinish()) {
            Log.d(TAG, "Exercise is finish");
            //call to the database to update the user stat
            updateUserStatInDatabase();

            //the exercise is finish we tell the observer to change fragment
            //no need to put back to true since we destroy the fragment in all case
            isExerciceFinish.setValue(true);
            return false;
        }
        return true;
    }

    private void updateUserStatInDatabase() {
        int nbCorrectAnswer = exercice.getNumberOfQuestion() - exercice.getNumberOfError();
        //update the user profile with the number of good and wrong responses
        Log.d(TAG, "updating user stat");

        Log.d(TAG, "correct answer " + nbCorrectAnswer);
        Log.d(TAG, "wrong answer " + exercice.getNumberOfError());

        userRepository.setUserStat(this.nameTypeOfExercise, nbCorrectAnswer,
                exercice.getNumberOfError(),exercice.getTypeReponse());
    }

    public Boolean checkResponse(String result, String correctResponse) {
        boolean responseBool;

        if(exercice.getTypeOfExercice() == TypeOfExercice.LETTER) {
            correctResponse = Utils.convertIntToStringMillier(exercice.getQuestion().getResult());
            Log.d(TAG, "Correct response  : "  + correctResponse);


            responseBool = correctResponse.equals(result);
        } else {
            int res = Integer.parseInt(result);

            responseBool =  res == exercice.getQuestion().getResult();
        }

        isResponseCorrect.postValue(responseBool);

        Log.d(TAG, "responseBool  : "  + isResponseCorrect.getValue());

        return responseBool;
    }

    public String getResultOfQuestion() {
        return String.valueOf(exercice.getQuestion().getResult());
    }

    public void updateNumberOfError() {
        Log.d(TAG, "update number of error before : " + this.exercice.getNumberOfError());
        this.exercice.setNumberOfError(this.exercice.getNumberOfError() + 1);
        Log.d(TAG, "update number of error after : " + this.exercice.getNumberOfError());
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
                return Utils.findNumberOfimage(value, nvalue, res);
            }
        }
        return new int[]{};
    }

    //this function is call on the dialog fragment
    //when the user validate
    public void changeQuestion() {
        if(isExerciseFinish()){
            Log.d(TAG,"Exercise is not finish");
            //we display the next question, the observer will update the UI
           nextQuestion();
        }
    }



}
