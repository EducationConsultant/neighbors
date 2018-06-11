package com.consul.edu.educationconsultant.asyncTasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.consul.edu.educationconsultant.model.Question;
import com.consul.edu.educationconsultant.retrofit.RedditAPI;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionAddTask extends AsyncTask<Question, Void, Question> {
    private Question questionRequest;
    private Question questionResult;

    private SharedPreferences sharedPreferences;

    public QuestionAddTask(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    /*public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }*/

    @Override
    protected Question doInBackground(Question... question) {

        /*String questionStr = strings[0];
        String ansOneStr = strings[1];
        String ansTwoStr = strings[2];
        String ansThreeStr = strings[3];
        String ansFourStr = strings[4];
        String eduLevelStr = strings[5];
        String categoryStr = strings[6];*/

        //User user = new User();

        questionRequest = question[0];
        //questionRequest = new Question(user, questionStr, ansOneStr, ansTwoStr, ansThreeStr, ansFourStr, eduLevelStr, categoryStr);
        questionResult = new Question();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RedditAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedditAPI client = retrofit.create(RedditAPI.class);
        Call<Question> questionResponse = client.insertQuestion(questionRequest);


        questionResponse.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                Log.w("successInsertQuestion","Question: " + response.body().getCategory());
                questionResult = new Question();
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Log.e("errorInsertUser","Server does not response.");
                questionResult = null;
            }
        });




        RedditAPI redditAPI = retrofit.create(RedditAPI.class);
        Call<JsonElement> callNotification = redditAPI.sendNotification();
        callNotification.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("notification success", "onNotificationResponse: Server Response: " + response.toString());
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("notification failed", "onNotificationFailure: Something went wrong: " + t.getMessage() );
            }
        });

        return questionResult;
    }
}
