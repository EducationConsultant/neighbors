package com.consul.edu.educationconsultant.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.consul.edu.educationconsultant.LoginActivity;
import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.adapters.CommentAdapter;
import com.consul.edu.educationconsultant.adapters.QuestionAdapter;
import com.consul.edu.educationconsultant.model.Comment;
import com.consul.edu.educationconsultant.model.Question;
import com.consul.edu.educationconsultant.model.User;
import com.consul.edu.educationconsultant.retrofit.RedditAPI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Svetlana on 4/14/2018.
 */


public class DetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "DetailsActivity";

    private CommentAdapter adapter;

    private ArrayList<Comment> commentList;

    private TextView txtUserFirstLastName;
    private TextView txtEmail;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    private Button btnSubmitAnswer;

    private TableLayout commentsTable;
    private EditText commentMessage;
    private ListView listCommentsView;
    private static boolean commented;

    private RadioGroup radioGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;

    private Question question;

    private SharedPreferences sharedPreferences;
    private String sharedPrefName;

    // -- dialog --
    private AlertDialog.Builder alertDialog;

    private String correctAns;
    private String userAns;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Log.d(TAG, "onCreate: started.");

        sharedPrefName = "currentUser";
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);

        btnSubmitAnswer = (Button) findViewById(R.id.submit_answer);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        rb1 = (RadioButton)findViewById(R.id.answer1);
        rb2 = (RadioButton)findViewById(R.id.answer2);
        rb3 = (RadioButton)findViewById(R.id.answer3);
        rb4 = (RadioButton)findViewById(R.id.answer4);

        commentList = new ArrayList<>();

        commentsTable = (TableLayout) findViewById(R.id.comments_table);
        commentMessage = (EditText) findViewById(R.id.comment_message);
        listCommentsView = (ListView) findViewById(R.id.listCommentsView);
        commented = false;

        question = new Question();

        getIncomingIntent();  // get data of selected question

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                switch(checkedId){
                    case R.id.answer1:
                        question.setAnswered(radioButton.getText().toString());
                        userAns = radioButton.getText().toString();
                        Log.d("<<<< SET QUESTION1 >>>>", "foo bar " + userAns );
                        break;
                    case R.id.answer2:
                        question.setAnswered(radioButton.getText().toString());
                        userAns = radioButton.getText().toString();
                        Log.d("<<<< SET QUESTION2 >>>>", "foo bar "+ userAns );
                        break;
                    case R.id.answer3:
                        question.setAnswered(radioButton.getText().toString());
                        userAns = radioButton.getText().toString();
                        Log.d("<<<< SET QUESTION3 >>>>", "foo bar "+ userAns );
                        break;
                    case R.id.answer4:
                        question.setAnswered(radioButton.getText().toString());
                        userAns = radioButton.getText().toString();
                        Log.d("<<<< SET QUESTION4 >>>>", "foo bar "+ userAns);
                        break;
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.details_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_details);
        navigationView.setNavigationItemSelectedListener(this);

        txtUserFirstLastName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_first_last_name);
        txtEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_email);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        String firstLastName = sharedPreferences.getString("user_first_name", "") + " " + sharedPreferences.getString("user_last_name", "");
        txtUserFirstLastName.setText(firstLastName);
        txtEmail.setText(firebaseUser.getEmail());

    }

    /**
     * get content od selected question from question list
    * */
    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        if(getIntent().hasExtra("description") && getIntent().hasExtra("username") && getIntent().hasExtra("category") ) {
            Log.d(TAG, "getIncomingIntent: found intent extras");

            String description = getIntent().getStringExtra("description");
            String username = getIntent().getStringExtra("username");
            String category = getIntent().getStringExtra("category");
            String answer1 = getIntent().getStringExtra("answer1");
            String answer2 = getIntent().getStringExtra("answer2");
            String answer3 = getIntent().getStringExtra("answer3");
            String answer4 = getIntent().getStringExtra("answer4");
            String eduLevel = getIntent().getStringExtra("eduLevel");
            String answered = getIntent().getStringExtra("answered");

            long id = getIntent().getLongExtra("id", 0);

            prepareCommentData(id);

            setQuestion( description, username, category, answer1, answer2, answer3, answer4, eduLevel,answered);
        }
    }


    /**
     *  set question atributtes
     *
     * */
    private void setQuestion(String description, String username, String category, String answer1, String answer2, String answer3, String answer4, String eduLevel, String answered) {
        TextView descriptionView  = findViewById(R.id.description);
        TextView usernameView = findViewById(R.id.username);
        TextView categoryView = findViewById(R.id.category);
        rb1 = findViewById(R.id.answer1);
        rb2 = findViewById(R.id.answer2);
        rb3 = findViewById(R.id.answer3);
        rb4 = findViewById(R.id.answer4);
        TextView eduLevelView = findViewById(R.id.eduLevel);

        correctAns = answer1; // save the correct ans for when resolving the correctness

        descriptionView.setText(description);
        usernameView.setText(username);
        categoryView.setText(category);
        rb1.setText(answer1);
        rb2.setText(answer2);
        rb3.setText(answer3);
        rb4.setText(answer4);
        eduLevelView.setText(eduLevel);
        if(answered.equals("")){
            btnSubmitAnswer.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);

            commentsTable.setVisibility(View.GONE);
            listCommentsView.setVisibility(View.GONE);

        }else{
            question.setAnswered(answered);
            btnSubmitAnswer.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);

            commentsTable.setVisibility(View.VISIBLE);
            listCommentsView.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.details_drawer_layout);
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
            Intent intent = new Intent(DetailsActivity.this, SettingsActivity.class);
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
            intent = new Intent(DetailsActivity.this, NavigationDrawerActivity.class);
        } else if (id == R.id.nav_archive) {
            // Danilo, put your acitivity here
            intent = new Intent(DetailsActivity.this, ArchiveActivity.class);
        } else if (id == R.id.nav_profile) {
            intent = new Intent(DetailsActivity.this,ProfileActivity.class);
        } else if (id == R.id.nav_logout) {
            intent = new Intent(DetailsActivity.this, LoginActivity.class);

            auth.signOut();

            firebaseUser = auth.getCurrentUser();
            if (firebaseUser == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(DetailsActivity.this, LoginActivity.class));
                finish();
            }
        }

        // Display the appropriate activity
        startActivity(intent);
        finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.details_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void submit_answer(View view) {
        Toast.makeText(this, "You resolved question :) ", Toast.LENGTH_SHORT).show();

        //Log.d("Is Correct: ", question.toString());

        // show the dialog
        alertDialog = new AlertDialog.Builder(DetailsActivity.this);

        // set up the dialog
        alertDialog.setTitle("Your answer is: ");
        alertDialog.setMessage("Foo bar");
        // check is answer correct
        if (correctAns.equals(userAns)){
            alertDialog.setMessage("Correct!");
        }else {
            alertDialog.setMessage("Incorrect!");
        }
        alertDialog.setCancelable(false); // cannot cancel the dialog
        // set up button on dialog
        alertDialog.setPositiveButton("Continue",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do something
                        btnSubmitAnswer.setVisibility(View.GONE);
                        radioGroup.setVisibility(View.GONE);

                        commentsTable.setVisibility(View.VISIBLE);
                        listCommentsView.setVisibility(View.VISIBLE);
                    }
                });

        // create dialog
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    /**
    *
    *  get all comments of one question, from database
    * */
    private void prepareCommentData(Long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RedditAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedditAPI redditAPI = retrofit.create(RedditAPI.class);
        Call<List<Comment>> call = redditAPI.getComments(id);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                Log.d(TAG, "onResponse: Server Response:" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());
                List<Comment> commentListResponse = response.body();

                for(Comment c : commentListResponse){
                    Comment newComment = new Comment();
                    newComment.setCreator(c.getCreator());
                    newComment.setText(c.getText());
                    newComment.setQuestion(c.getQuestion());

                    if (!commentListResponse.contains(newComment)) {
                        commentList.add(newComment);

                        adapter = new CommentAdapter(DetailsActivity.this, R.layout.adapter_comment_view_layout, commentList);
                        listCommentsView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, "onFailure:Something went wrong " + t.getMessage());
                Toast.makeText(DetailsActivity.this, "Something went wrong" , Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * inert new comment for one question
     * */
    private void insertCommentData() {
        final EditText comment_message = (EditText) findViewById(R.id.comment_message);
        long questionId = getIntent().getLongExtra("id", 0);
        String text = comment_message.getText().toString();
        User creator = getLoggedInUser();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RedditAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedditAPI redditAPI = retrofit.create(RedditAPI.class);
        Call<Comment> call = redditAPI.insertComment(questionId, new Comment(creator, question, text));


        commented = true;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("commented","true");
        editor.apply();


        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Log.d(TAG, "onResponse: Server Response: " + response.toString());

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage() );
                Toast.makeText(DetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = auth.getCurrentUser();
        sharedPrefName = "currentUser";
    }


    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);
        setListenerOnCommentMessage();




    }


    /**
    *  set listener on filed commentMessage, to add new comment
    *
    * */
    private void setListenerOnCommentMessage() {
        commentMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (commentMessage.getRight() - commentMessage.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        insertCommentData();

                        // refresh activity
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);

                        return true;
                    }
                }
                return false;
            }
        });

    }


    /**
    * get loggedIn user from sharedPreference
    * */
    private User getLoggedInUser() {
        User user = new User();
        Long user_id  = sharedPreferences.getLong("user_id", 1);
        String user_first_name = sharedPreferences.getString("user_first_name", "a");
        String user_last_name = sharedPreferences.getString("user_last_name", "a");
        String user_email = sharedPreferences.getString("user_email", "a@gmail.com");
        String user_password = sharedPreferences.getString("user_password", "a");
        user.setId(user_id);
        user.setFirstName(user_first_name);
        user.setLastName(user_last_name);
        user.setEmail(user_email);
        user.setPassword(user_password);
        return user;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get value of commented --- to show comments if comment is sent
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("commented", "false");
        Toast.makeText(this, "Vrendost onResume " + name , Toast.LENGTH_SHORT).show();

        if(name == "true") {
            Toast.makeText(this, "Usao u if " + name , Toast.LENGTH_SHORT).show();
            btnSubmitAnswer.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
            commentsTable.setVisibility(View.VISIBLE);
            listCommentsView.setVisibility(View.VISIBLE);
        }
    }
}



