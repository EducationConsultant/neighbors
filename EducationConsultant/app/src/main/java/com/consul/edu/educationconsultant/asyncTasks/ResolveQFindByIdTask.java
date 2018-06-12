package com.consul.edu.educationconsultant.asyncTasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.consul.edu.educationconsultant.model.Question;
import com.consul.edu.educationconsultant.model.ResolveQuestion;
import com.consul.edu.educationconsultant.model.User;
import com.consul.edu.educationconsultant.retrofit.RedditAPI;
import com.consul.edu.educationconsultant.retrofit.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResolveQFindByIdTask extends AsyncTask<String, Void, Question> {
    private ResolveQuestion resolvedQRequest;
    private ResolveQuestion resolvedQResult;

    private SharedPreferences sharedPreferences;

    public ResolveQFindByIdTask(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected Question doInBackground(String... strings) {
        String userIdStr = strings[0];
        String questionIdStr = strings[1];

        Long userId = Long.parseLong(userIdStr);
        Long questionId = Long.parseLong(questionIdStr);

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RedditAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedditAPI client = retrofit.create(RedditAPI.class);
        Call<ResolveQuestion> resolveQResponse = client.findQuestionByUser(userId,questionId);

        resolveQResponse.enqueue(new Callback<ResolveQuestion>() {
            @Override
            public void onResponse(Call<ResolveQuestion> call, Response<ResolveQuestion> response) {
                Log.e("resolveQuestion","success: " + response.body());

              /*  editor.putLong("resolvedQ_id", response.body().getId());
                editor.apply();

                editor.putLong("resolvedQ_idUser",response.body().getIdUsera());
                editor.apply();

                editor.putLong("resolvedQ_idQuestion",response.body().getIdQuestion());
                editor.apply();

                editor.putString("resolvedQ_answer",response.body().getAnswer());
                editor.apply();*/
            }

            @Override
            public void onFailure(Call<ResolveQuestion> call, Throwable t) {
                Log.e("resolveQuestion","Server does not response.");
            }
        });

        return null;
    }
}
