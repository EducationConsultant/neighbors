package com.consul.edu.educationconsultant.retrofit;

import com.consul.edu.educationconsultant.model.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Svetlana on 5/16/2018.
 */

public interface RedditAPI {
    // call ipconfig
    String BASE_URL = "http://192.168.0.14:8095/educon/";

    @Headers("Content-Type: application/json")
    @GET("question")
    Call<List<Question>> getData();

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