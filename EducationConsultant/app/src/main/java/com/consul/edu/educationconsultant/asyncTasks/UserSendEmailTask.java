package com.consul.edu.educationconsultant.asyncTasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.consul.edu.educationconsultant.model.User;
import com.consul.edu.educationconsultant.retrofit.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserSendEmailTask extends AsyncTask<String, Void, User> {

    private User userRequest;
    private User userResult;

    @Override
    protected User doInBackground(String... strings) {
        String userEmail = strings[0];
        userRequest = new User("","",userEmail,"");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserClient client = retrofit.create(UserClient.class);
        Call<User> userResponse = client.sendEmail(userRequest);


        userResponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.w("successSendEmail","doInBackground: User email: " + response.body().getEmail());
                userResult = new User(response.body().getId(),response.body().getFirstName(),response.body().getLastName(),response.body().getEmail(),response.body().getPassword());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("errSendMail","findByEmail: Server does not response.");
                userResult = null;
            }
        });

        return userResult;
    }
}
