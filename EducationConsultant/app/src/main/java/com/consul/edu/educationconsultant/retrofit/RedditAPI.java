package com.consul.edu.educationconsultant.retrofit;

import com.consul.edu.educationconsultant.model.Comment;
import com.consul.edu.educationconsultant.model.Question;
import com.consul.edu.educationconsultant.model.ResolveQuestion;
import com.consul.edu.educationconsultant.model.User;
import com.consul.edu.educationconsultant.wrappers.FilterWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Svetlana on 5/16/2018.
 */

public interface RedditAPI {
    // call ipconfig
    String BASE_URL = "http://192.168.1.4:8095/educon/";


    @Headers("Content-Type: application/json")
    @GET("question/commentsAll/{questionId}")
    Call<List<Comment>> getComments(@Path("questionId") Long questionId);

    @Headers("Content-Type: application/json")
    @POST("question/comment/{questionId}")
    Call<Comment> insertComment(@Path("questionId") Long questionId, @Body Comment comment);

    @Headers("Content-Type: application/json")
    @GET("question")
    Call<List<Question>> getData();

    @Headers("Content-Type: application/json")
    @GET("question/{id}")
    Call<Question> getQuestion(@Path("id") Long id);

    @Headers("Content-Type: application/json")
    @POST("question")
    Call<Question> insertQuestion(@Body Question question);

    @Headers("Content-Type: application/json")
    @PUT("question/{id}")
    Call<Question> updateQuestion(@Path("id") Long id, @Body Question question);

	@Headers("Content-Type: application/json")
    @PUT("question/filters/{radius}")
    Call<List<Question>> findByFilters(@Path("radius") int radius, @Body FilterWrapper filters);

	@Headers("Content-Type: application/json")
    @POST("resolvequestion")
    Call<ResolveQuestion> insertResolveQuestion(@Body ResolveQuestion resolveQuestion);

}





/** RETROFIT
@GET("users/{user}/repos")
Call<List<Repo>> listRepos(@Path("user") String user);
        **/