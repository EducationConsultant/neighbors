package com.consul.edu.educationconsultant.retrofit;

import com.consul.edu.educationconsultant.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserClient {

    String BASE_URL = "http://192.168.1.3:8095/educon/";


    // find by email and password
    @Headers("Content-Type: application/json")
    @PUT("user")
    Call<User> findByEmailAndPassword(@Body User user);

    // find by email
    @Headers("Content-Type: application/json")
    @PUT("user/email")
    Call<User> findByEmail(@Body User user);

    // insert user
    @Headers("Content-Type: application/json")
    @POST("user")
    Call<User> insertUser(@Body User user);

    // update user
    @Headers("Content-Type: application/json")
    @PUT("user/{id}")
    Call<User> updateUser(@Path("id") Long id, @Body User user);

    // update user password
    @Headers("Content-Type: application/json")
    @PUT("user/{id}/password")
    Call<User> updateUserPassword(@Path("id") Long id, @Body User user);

    // forgot password
    @Headers("Content-Type: application/json")
    @PUT("user/forgotPassword")
    Call<User> sendEmail(@Body User user);
}
