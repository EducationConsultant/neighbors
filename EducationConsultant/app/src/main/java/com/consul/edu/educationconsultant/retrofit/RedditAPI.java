package com.consul.edu.educationconsultant.retrofit;

import com.consul.edu.educationconsultant.model.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Svetlana on 5/16/2018.
 */

public interface RedditAPI {
    // call ipconfig
    String BASE_URL = "http://192.168.43.209:8095/educon/";

    @Headers("Content-Type: application/json")
    @GET("question")
    Call<List<Question>> getData();

    @Headers("Content-Type: application/json")
    @GET("question")
    Call<List<Question>> getQuestions();

    @Headers("Content-Type: application/json")
    @GET("question/{id}")
    Call<Question> getQuestion(@Path("id") Long id);

    @Headers("Content-Type: application/json")
    @PUT("question/eduLevels")
    Call<List<Question>> findByEduLevel(); // TODO

    @Headers("Content-Type: application/json")
    @PUT("question/categories")
    Call<List<Question>> findByCategories(); // TODO

    @Headers("Content-Type: application/json")
    @POST("question")
    Call<Question> insertQuestion(@Body Question question);

    @Headers("Content-Type: application/json")
    @PUT("question/{id}")
    Call<Question> updateQuestion(@Path("id") Long id, @Body Question question);

}


/**
 MITCH
    String BASE_URL = "https://www.reddit.com/";

    @Headers("Content-Type: application/json")
    @GET(".json")
    Call<Feed> getData();

    **/


/**
 *
 ASISTENT
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.ADD)
    Call<ResponseBody> add(@Body TagToSend tag);

    **/

/** RETROFIT
@GET("users/{user}/repos")
Call<List<Repo>> listRepos(@Path("user") String user);
 **/