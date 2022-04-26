package com.example.arithmos.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.arithmos.db.QuestionRepository;
import com.example.arithmos.db.RepositoryCallback;
import com.example.arithmos.db.Result;
import com.example.arithmos.db.UserRepository;
import com.example.arithmos.db.allUserStat;
import com.example.arithmos.model.AbstractExercice;
import com.example.arithmos.model.ExerciceAdd;
import com.example.arithmos.model.ExerciceDiv;
import com.example.arithmos.model.ExerciceMult;
import com.example.arithmos.model.ExerciceSous;
import com.example.arithmos.model.ExerciseRandom;
import com.example.arithmos.model.ExoStat;
import com.example.arithmos.model.Question;
import com.example.arithmos.model.TypeOfExercice;
import com.example.arithmos.utils.Utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class ExerciceViewModel extends AndroidViewModel {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    private AbstractExercice exercice;

    private String nameTypeOfExercise;

    //boolean to let the dialog display the right answer or not
    private Boolean displayAnswer = false;

    //we will use this to display the user response in the dialog
    private String userResponse;

    //For debug only
    private final String TAG = "EXERCICEVIEWMODEL";


    //This is the object that the view is observing
    public MutableLiveData<Question> currentQuestion = new MutableLiveData<>();
    //boolean to check if exercise is finish while in the view
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
    public void createExercice(String typeOfExercice, int difficulty, int select, int type, int table) {

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
                exercice = new ExerciceAdd(difficulty, selectExercise, typeOfRes, table);
                break;
            case "sous":
                exercice = new ExerciceSous(difficulty, selectExercise, typeOfRes);
                break;
            case "mult":
                exercice = new ExerciceMult(difficulty, selectExercise, typeOfRes, table);
                break;
            case "div":
                exercice = new ExerciceDiv(difficulty, selectExercise, typeOfRes);
                break;
            default:
                exercice = new ExerciceAdd(difficulty, selectExercise, typeOfRes, table);
        }

        //we use a callback to create the exercice
        //because we need to access the database and it's not possible in UI thread
        questionRepository.getTenQuestionType(result -> {
            if(result instanceof Result.Success) {
                List<Question> resData = ((Result.Success<List<Question>>) result).data;

                exercice.createAllQuestion(resData, difficulty);

                Log.d(TAG, "SIZE OF Question : "
                        + String.valueOf(resData.size()));
                Log.d(TAG, "TITLE Question 1 : "
                        + String.valueOf(resData.get(0).getTitle()));

                currentQuestion.postValue(exercice.getQuestion());
                isLoadingOK.postValue(true);
            } else if (result instanceof Result.Error){
                //TODO : find a better way to handle error case
                currentQuestion.postValue(new Question("ERROR", "ERROR", 2,
                        "fleur"));
                isLoadingOK.postValue(false);
            }
        },typeOfExercice);

    }

    public void createRandomExercise(int difficulty) {

        exercice = new ExerciseRandom(difficulty);

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);

        //if none of the exercise are activate we do nothing
        /*if(pref.getBoolean("add", false)
                && pref.getBoolean("sous", false)
                && pref.getBoolean("mult", false)
                && pref.getBoolean("div", false)) {
            Log.d(TAG,"ici le ro");
            Log.d(TAG,""+pref.getBoolean("add", true));
            return;
        }*/


        userRepository.geAlltUserStat(result -> {
            if(result instanceof Result.Success) {
                List<allUserStat> allUserStat = ((Result.Success<List<allUserStat>>) result).data;
                int nbWrongResponses = 0;

                for(allUserStat userStat : allUserStat) {
                    nbWrongResponses += userStat.incorrect;
                }

                Log.d(TAG, "Total number of Wrong Responses " + nbWrongResponses);
                HashMap<String, Float> userRatios = new HashMap<>();

                for(allUserStat userStat : allUserStat) {
                    float ratio = (float) userStat.incorrect / nbWrongResponses;

                    userRatios.put(userStat.exo, ratio);
                }

                for (String key : userRatios.keySet()) {
                    Log.d(TAG,"exo=" + key + "  ratio=" + userRatios.get(key));

                    if(userRatios.get(key) != null) {

                        int r = 0;
                        float calculateRatio = userRatios.get(key);

                        if(calculateRatio < 0.1) {
                            //round toward 0 since the number is small and but represent a part of exercise
                            r = (int) Math.ceil(calculateRatio);
                        } else {
                            r = (int) (calculateRatio* 10);
                        }

                        Log.d(TAG,"on va chercher " + r + " quetions");

                        if(r != 0 && pref.getBoolean(key, true)) {
                            //second callback
                            questionRepository.getAllQuestionByTypeAndLimit(result1 -> {
                                if (result1 instanceof Result.Success) {
                                    List<Question> resData =
                                            ((Result.Success<List<Question>>) result1).data;

                                    Log.d(TAG, "For type of exercise " + key
                                            + " we have " +resData.size() + " questions");

                                    //cast to random exercise to call the right constructor
                                    ExerciseRandom exerciseR = (ExerciseRandom) exercice;

                                    exerciseR.createAllQuestion(resData, key);

                                    if(currentQuestion.getValue() == null) {
                                        currentQuestion.postValue(exercice.getQuestion());
                                    }
                                } else {
                                    Log.d(TAG, "Error when loading questions for type " +
                                            key);
                                }

                            }, key, r);
                        }
                    }
                }
                isLoadingOK.postValue(true);
            } else {
                isLoadingOK.postValue(false);
            }
        });

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

            int lev = Utils.levenshteinDistance(result, correctResponse);

            Log.d(TAG, "lev  : "  + lev);

            float ratio = ((float)lev) / Math.max(result.length(), correctResponse.length());

            Log.d(TAG, "ratio   : "  + ratio);

            responseBool = ratio < 0.3;

            displayAnswer = ratio > 0.0;

            userResponse = result;
        } else {
            int res = Integer.parseInt(result);

            responseBool =  res == exercice.getQuestion().getResult();
        }

        isResponseCorrect.postValue(responseBool);

        Log.d(TAG, "responseBool  : "  + responseBool);

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

    public AbstractExercice getExercice() {
        return exercice;
    }

    public String getImagesTypes() {
        return Objects.requireNonNull(currentQuestion.getValue()).getImageType();
    }

    public Boolean getDisplayAnswer() {
        return displayAnswer;
    }

    public void setDisplayAnswer(Boolean displayAnswer) {
        this.displayAnswer = displayAnswer;
    }

    public String getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(String userResponse) {
        this.userResponse = userResponse;
    }
}
