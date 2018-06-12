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

public class RegistrationTask extends AsyncTask<String, Void, User> {
    private User userRequest;
    private User userResult;

    private SharedPreferences sharedPreferences;

    public RegistrationTask(SharedPreferences sharedPreferences){
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

        UserClient userClient = retrofit.create(UserClient.class);
        Call<User> userResponse = userClient.insertUser(userRequest);

        userResponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.w("insertUserSuccess","insert success: " + response.body().getEmail());
                userResult = new User(response.body().getId(),response.body().getFirstName(),response.body().getLastName(),response.body().getEmail(),response.body().getPassword());

                editor.putLong("user_id", response.body().getId());
                editor.apply();

                editor.putString("user_first_name",response.body().getFirstName());
                editor.apply();

                editor.putString("user_last_name",response.body().getLastName());
                editor.apply();

                editor.putString("user_email",response.body().getEmail());
                editor.apply();

                editor.putString("user_password",response.body().getPassword());
                editor.apply();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("insertUserFail","insert failed");
            }
        });

        return userResult;
    }
}
