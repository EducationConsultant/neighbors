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

public class UserUpdateProfileTask extends AsyncTask<String, Void, User> {

    private User userRequest;
    private User userResult;

    private SharedPreferences sharedPreferences;

    public UserUpdateProfileTask(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected User doInBackground(String... strings) {
        String userIdStr = strings[0];
        Long userId = Long.parseLong(userIdStr);
        String userFirstName = strings[1];
        String userLastName = strings[2];
        String userEmail = strings[3];

        userRequest = new User(userFirstName,userLastName,userEmail,"");

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserClient client = retrofit.create(UserClient.class);
        Call<User> userResponse = client.updateUser(userId, userRequest);

        userResponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.w("successUpdateProfile","User email: " + response.body().getEmail());
                userResult = new User(response.body().getId(),response.body().getFirstName(),response.body().getLastName(),response.body().getEmail(),response.body().getPassword());

                editor.putString("user_first_name",response.body().getFirstName());
                editor.apply();

                editor.putString("user_last_name",response.body().getLastName());
                editor.apply();

                editor.putString("user_email",response.body().getEmail());
                editor.apply();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("errorUpdateProfile","Server does not response.");
                userResult = null;
            }
        });

        return userResult;
    }
}
