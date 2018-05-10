package com.consul.edu.educationconsultant.activities;

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
import android.view.View;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;

/**
 * Created by Svetlana on 4/14/2018.
 */


public class DetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "DetailsActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.details_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_details);
        navigationView.setNavigationItemSelectedListener(this);



    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        if(getIntent().hasExtra("title") && getIntent().hasExtra("description") && getIntent().hasExtra("username") && getIntent().hasExtra("category") ) {
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

            setQuestion(title, description, username, category, answer1, answer2, answer3, answer4, eduLevel);
        }
    }

    private void setQuestion(String title, String description, String username, String category, String answer1, String answer2, String answer3, String answer4, String eduLevel) {
        TextView titleView = findViewById(R.id.title);
        TextView descriptionView  = findViewById(R.id.description);
        TextView usernameView = findViewById(R.id.username);
        TextView categoryView = findViewById(R.id.category);
        RadioButton rb1 = findViewById(R.id.answer1);
        RadioButton rb2 = findViewById(R.id.answer2);
        RadioButton rb3 = findViewById(R.id.answer3);
        RadioButton rb4 = findViewById(R.id.answer4);
        TextView eduLevelView = findViewById(R.id.eduLevel);

        titleView.setText(title);
        descriptionView.setText(description);
        usernameView.setText(username);
        categoryView.setText(category);
        rb1.setText(answer1);
        rb2.setText(answer2);
        rb3.setText(answer3);
        rb4.setText(answer4);
        eduLevelView.setText(eduLevel);
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

            // TODO: Uncomment this part when development is finished
            /*
            auth.signOut();

            FirebaseUser user = auth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(DetailsActivity.this, LoginActivity.class));
                finish();
            }*/
        }

        // Display the appropriate activity
        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.details_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void submit_answer(View view) {
        Toast.makeText(this, "You resolved question :) ", Toast.LENGTH_SHORT).show();
    }
}
