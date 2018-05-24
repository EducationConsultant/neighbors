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

/**
 *
 * First parameter is the type of object used to pass any task parameters to the doInBackground() method.
 * Second parameter is the type of object used to indicate task progress.
 * Third parameter is the type of the task result.
 *
 * */

public class UserFindByEmailTask extends AsyncTask<String, Void, User> {

    private User userRequest;
    private User userResult;

    private SharedPreferences sharedPreferences;

    public UserFindByEmailTask(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected User doInBackground(String... strings) {
        String userEmail = strings[0];
        userRequest = new User("","",userEmail,"");

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserClient client = retrofit.create(UserClient.class);
        Call<User> userResponse = client.findByEmail(userRequest);


        userResponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.w("successInBackground","doInBackground: User email: " + response.body().getEmail());
                userResult = new User(response.body().getId(),response.body().getFirstName(),response.body().getLastName(),response.body().getEmail(),response.body().getPassword());

                editor.putLong("user_id",userResult.getId());
                editor.apply();
                editor.putString("user_password",userResult.getPassword());
                editor.apply();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("notResponse","findByEmail: Server does not response.");
                //Toast.makeText(LoginActivity.this, "findByEmail: Server does not response.", Toast.LENGTH_LONG).show();
                userResult = null;
            }
        });

        return userResult;
    }
}
