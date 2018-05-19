package com.consul.edu.educationconsultant.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.consul.edu.educationconsultant.LoginActivity;
import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.model.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Svetlana on 4/14/2018.
 */


public class DetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "DetailsActivity";

    private TextView txtUserFirstLastName;
    private TextView txtEmail;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    private Button btnSubmitAnswer;

    private TextView questionTitle;
    private TextView questionDescription;
    private TextView questionCategory;
    private TextView questionEduLevel;
    private TextView questionUsername;
    private TableLayout commentsTable;
    private EditText commentMessage;

    private RadioGroup radioGroup;
    private RadioButton rbAnswer1;
    private RadioButton rbAnswer2;
    private RadioButton rbAnswer3;
    private RadioButton rbAnswer4;

    private Question question;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Log.d(TAG, "onCreate: started.");

        btnSubmitAnswer = (Button) findViewById(R.id.submit_answer);

        questionTitle = findViewById(R.id.title);
        questionDescription = (TextView) findViewById(R.id.description);
        questionCategory = (TextView) findViewById(R.id.category);
        questionEduLevel = (TextView) findViewById(R.id.eduLevel);
        questionUsername = (TextView) findViewById(R.id.username);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        rbAnswer1 = (RadioButton)findViewById(R.id.answer1);
        rbAnswer2 = (RadioButton)findViewById(R.id.answer2);
        rbAnswer3 = (RadioButton)findViewById(R.id.answer3);
        rbAnswer4 = (RadioButton)findViewById(R.id.answer4);

        commentsTable = (TableLayout) findViewById(R.id.comments_table);
        commentMessage = (EditText) findViewById(R.id.comment_message);

        question = new Question();

        getIncomingIntent();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                switch(checkedId){
                    case R.id.answer1:
                        question.setAnswered(radioButton.getText().toString());
                        break;
                    case R.id.answer2:
                        question.setAnswered(radioButton.getText().toString());
                        break;
                    case R.id.answer3:
                        question.setAnswered(radioButton.getText().toString());
                        break;
                    case R.id.answer4:
                        question.setAnswered(radioButton.getText().toString());
                        break;
                }
            }
        });

        commentMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                                final int DRAWABLE_LEFT = 0;
                                final int DRAWABLE_TOP = 1;
                                final int DRAWABLE_RIGHT = 2;
                               final int DRAWABLE_BOTTOM = 3;

                                       if(event.getAction() == MotionEvent.ACTION_UP) {
                                        if(event.getRawX() >= (commentMessage.getRight() - commentMessage.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                                // your action here
                                                        Toast.makeText(DetailsActivity.this,"Send comment",Toast.LENGTH_LONG).show();
                                                return true;
                                            }
                                    }
                                return false;
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

        txtUserFirstLastName.setText(firebaseUser.getDisplayName());
        txtEmail.setText(firebaseUser.getEmail());

    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        if(getIntent().hasExtra("description") && getIntent().hasExtra("username") && getIntent().hasExtra("category") ) {
            Log.d(TAG, "getIncomingIntent: found intent extras");

            String title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");
            String username = getIntent().getStringExtra("username");
            String category = getIntent().getStringExtra("category");
            String answer1 = getIntent().getStringExtra("answer1");
            String answer2 = getIntent().getStringExtra("answer2");
            String answer3 = getIntent().getStringExtra("answer3");
            String answer4 = getIntent().getStringExtra("answer4");
            String eduLevel = getIntent().getStringExtra("eduLevel");
            String answered = getIntent().getStringExtra("answered");

            setQuestion(title, description, username, category, answer1, answer2, answer3, answer4, eduLevel,answered);
        }
    }

    private void setQuestion(String title, String description, String username, String category, String answer1, String answer2, String answer3, String answer4, String eduLevel, String answered) {

        questionTitle.setText(title);
        questionDescription.setText(description);
        questionUsername.setText(username);
        questionCategory.setText(category);
        questionEduLevel.setText(eduLevel);
        rbAnswer1.setText(answer1);
        rbAnswer2.setText(answer2);
        rbAnswer3.setText(answer3);
        rbAnswer4.setText(answer4);

        if(answered.equals("")){
            btnSubmitAnswer.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);

            commentsTable.setVisibility(View.GONE);
        }else{
            question.setAnswered(answered);
            btnSubmitAnswer.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);

            commentsTable.setVisibility(View.VISIBLE);
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


        btnSubmitAnswer.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);

        commentsTable.setVisibility(View.VISIBLE);

    }
}
