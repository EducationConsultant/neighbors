package com.consul.edu.educationconsultant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.consul.edu.educationconsultant.LoginActivity;
import com.consul.edu.educationconsultant.adapters.QuestionAdapter;
import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.listeners.RecyclerTouchListener;
import com.consul.edu.educationconsultant.model.Question;
import com.consul.edu.educationconsultant.retrofit.RedditAPI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// TODO : baza ---> asinhroni zadaci ---> onResume()

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "NavigationDrawrActivity";
    private static final String BASE_URL = "http://192.168.43.98:8095/educon/";

    private Button btnLogout;
    private FirebaseAuth auth;

    private List<Question> questionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private QuestionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addQuestion = new Intent(NavigationDrawerActivity.this, AddQuestionActivity.class);
                startActivity(addQuestion);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // RecycleView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new QuestionAdapter(this, questionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        // separator
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // set the adapter
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Question question = questionList.get(position);
                Toast.makeText(getApplicationContext(), question.getDescription() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(NavigationDrawerActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * This method gets called whenever an item in the drawer is clicked.
     *
     * @param item Clicked item.
     * */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_home) {
            intent = new Intent(NavigationDrawerActivity.this,NavigationDrawerActivity.class);
        } else if (id == R.id.nav_archive) {
            intent = new Intent(NavigationDrawerActivity.this,ArchiveActivity.class);
        } else if (id == R.id.nav_profile) {
            intent = new Intent(NavigationDrawerActivity.this,ProfileActivity.class);
        } else if (id == R.id.nav_logout) {
            intent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);

            // TODO: Uncomment this part when development is finished
            /*
            auth.signOut();

            FirebaseUser user = auth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(NavigationDrawerActivity.this, LoginActivity.class));
                finish();
            }*/
        }

        // Display the appropriate activity
        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareQuestionData() {

        Question question = new Question("Question A", "User 1", "This is my first question", "Mathematics", "a1", "a2" ,"a3", "a4", "Elementary School", "a1", "a1");
        questionList.add(question);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedditAPI redditAPI = retrofit.create(RedditAPI.class);

        Call<List<Question>> call = redditAPI.getData();
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                Log.d(TAG, "onResponse: Server Response:" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());
                List<Question> questionListResponse = response.body();

                for(int i = 0; i < questionListResponse.size(); i++) {
                    String title = questionListResponse.get(i).getTitle();
                    String username  = questionListResponse.get(i).getUsername();
                    String description  = questionListResponse.get(i).getDescription();
                    String category  = questionListResponse.get(i).getCategory();
                    String answer1  = questionListResponse.get(i).getAnswer1();
                    String answer2  = questionListResponse.get(i).getAnswer2();
                    String answer3  = questionListResponse.get(i).getAnswer3();
                    String answer4  = questionListResponse.get(i).getAnswer4();
                    String eduLevel  = questionListResponse.get(i).getEduLevel();
                    String correctAns  = questionListResponse.get(i).getCorrectAns();
                    String answered  = questionListResponse.get(i).getAnswered();
                    Question q = new Question(title, username, description, category, answer1, answer2, answer3, answer4, eduLevel,correctAns,answered);
                    questionList.add(q);
                    Toast.makeText(NavigationDrawerActivity.this, "Broj pitanja dodatih u questionList: " + questionList.size(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Log.e(TAG, "onFailure:Something went wrong " + t.getMessage());
                Toast.makeText(NavigationDrawerActivity.this, "Something went wrong" , Toast.LENGTH_SHORT).show();
            }
        });

        Toast.makeText(NavigationDrawerActivity.this, "Broj pitanja dodatih na kraju metode: " + questionList.size(), Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        prepareQuestionData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
