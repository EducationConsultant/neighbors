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

public class RegistrationUserTask extends AsyncTask<String, Void, User> {
    private User userRequest;
    private User userResult;

    private SharedPreferences sharedPreferences;

    public RegistrationUserTask(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected User doInBackground(String... strings) {
        String userFirstName = strings[0];
        String userLastName = strings[1];
        String userEmail = strings[2];
        String userPassword = strings[3];

        userRequest = new User(userFirstName,userLastName,userEmail,userPassword);

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserClient client = retrofit.create(UserClient.class);
        Call<User> userResponse = client.insertUser(userRequest);


        userResponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.w("successInsertUser","User email: " + response.body().getEmail());
                userResult = new User(response.body().getId(),response.body().getFirstName(),response.body().getLastName(),response.body().getEmail(),response.body().getPassword());

                editor.putLong("user_id",userResult.getId());
                editor.apply();
                editor.putString("user_password",userResult.getPassword());
                editor.apply();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("errorInsertUser","Server does not response.");
                userResult = null;
            }
        });

        return userResult;
    }
}
