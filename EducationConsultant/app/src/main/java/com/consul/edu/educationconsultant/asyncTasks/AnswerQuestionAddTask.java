package com.consul.edu.educationconsultant.asyncTasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.consul.edu.educationconsultant.model.ResolveQuestion;
import com.consul.edu.educationconsultant.retrofit.RedditAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnswerQuestionAddTask extends AsyncTask<ResolveQuestion, Void, ResolveQuestion> {

    private ResolveQuestion rqRequest;
    private ResolveQuestion rqResult;

    private SharedPreferences sharedPreferences;

   /* public ResolveQuestionAddTask(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }*/

    @Override
    protected ResolveQuestion doInBackground(ResolveQuestion... resolveQuestions){

        rqRequest = resolveQuestions[0];
        rqResult = new ResolveQuestion();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RedditAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedditAPI client = retrofit.create(RedditAPI.class);
        Call<ResolveQuestion> rqResponse = client.insertResolveQuestion(rqRequest);

        rqResponse.enqueue(new Callback<ResolveQuestion>() {
            @Override
            public void onResponse(Call<ResolveQuestion> call, Response<ResolveQuestion> response) {
                Log.w("successInsert","ResolveQuestion: " + response.body().toString());
                rqResult = new ResolveQuestion();
            }

            @Override
            public void onFailure(Call<ResolveQuestion> call, Throwable t) {
                Log.e("errorInsert","Server does not response.");
                rqResult = null;
            }
        });

        return rqResult;
    }

}
