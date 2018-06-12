package com.consul.edu.educationconsultant.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.consul.edu.educationconsultant.adapters.ArchiveAdapter;
import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.model.Question;
import com.consul.edu.educationconsultant.model.ResolveQuestion;
import com.consul.edu.educationconsultant.model.User;
import com.consul.edu.educationconsultant.retrofit.RedditAPI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArchiveActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionBar;

    private List<ResolveQuestion> questionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArchiveAdapter mAdapter;

    private SharedPreferences sharedPreferences;
    private String sharedPrefName;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        toolbar = (Toolbar) findViewById(R.id.toolbar_archive);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        sharedPrefName = "currentUser";
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        // getSupportActionBar: using the toolbar from the Support Library
        actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        recyclerView = (RecyclerView) findViewById(R.id.archive_view);

        mAdapter = new ArchiveAdapter(this, questionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareQuestionData();

        // separator
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // set the adapter
        recyclerView.setAdapter(mAdapter);


    }

    private void prepareQuestionData() {

        Long userId = sharedPreferences.getLong("user_id", -1L);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RedditAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedditAPI client = retrofit.create(RedditAPI.class);
        Call<List<ResolveQuestion>> rqResponse = client.getResolveQuestion(userId);

        rqResponse.enqueue(new Callback<List<ResolveQuestion>>() {
            @Override
            public void onResponse(Call<List<ResolveQuestion>> call, Response<List<ResolveQuestion>> response) {
                List<ResolveQuestion> rqResponse = response.body();

                for (ResolveQuestion rq:rqResponse){
                    ResolveQuestion newRQ = new ResolveQuestion();
                    newRQ.setCorrectAns(rq.getCorrectAns());
                    newRQ.setAnswer(rq.getAnswer());
                    newRQ.setIdQuestion(rq.getIdQuestion());
                    newRQ.setIdUsera(rq.getIdUsera());
                    newRQ.setQuestionText(rq.getQuestionText());

                    if (!questionList.contains(newRQ)){
                        questionList.add(newRQ);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResolveQuestion>> call, Throwable t) {
                Log.e("ArchiveActivity: ", "Something went wrong");
            }
        });

        //mAdapter.notifyDataSetChanged();
    }
}
