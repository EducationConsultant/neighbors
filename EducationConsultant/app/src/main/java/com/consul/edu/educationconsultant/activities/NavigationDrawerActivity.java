package com.consul.edu.educationconsultant.activities;

import android.Manifest;
import android.content.Intent;


import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.consul.edu.educationconsultant.model.User;
import com.consul.edu.educationconsultant.retrofit.RedditAPI;
import com.consul.edu.educationconsultant.wrappers.FilterWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "NavActivity";

    private Button btnLogout;
    private TextView txtUserFirstLastName;
    private TextView txtEmail;

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    private List<Question> questionList;
    private RecyclerView recyclerView;
    private QuestionAdapter mAdapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesSettings;
    private String sharedPrefName;
    private String sharedPrefNameSettings;

    private List<String> filtersStr;

    // -- location --
    private LocationManager locationManager;
    private LocationListener locationListener;

    // ----

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        sharedPrefName = "currentUser";
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);

        sharedPrefNameSettings = "Settings" + "_" + firebaseUser.getEmail();
        sharedPreferencesSettings = getSharedPreferences(sharedPrefNameSettings,MODE_PRIVATE);

        // -- location --
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Location: ", location.toString());
                Log.d("Longitude: ", String.valueOf(location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }; // end listener
        // ----

        questionList = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addQuestion = new Intent(NavigationDrawerActivity.this, AddQuestionActivity.class);
                startActivity(addQuestion);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        txtUserFirstLastName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_first_last_name);
        txtEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_email);

        String firstLastName = sharedPreferences.getString("user_first_name", "") + " " + sharedPreferences.getString("user_last_name", "");
        txtUserFirstLastName.setText(firstLastName);
        txtEmail.setText(firebaseUser.getEmail());

        // RecycleView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new QuestionAdapter(this, questionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        // prepareQuestionData();

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
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
            auth.signOut();
            firebaseUser = auth.getCurrentUser();
            if (firebaseUser == null) {
                // user auth state is changed - user is null
                // launch login activity
                intent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }

        // Display the appropriate activity
        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        String firstLastName = sharedPreferences.getString("user_first_name", "") + " " + sharedPreferences.getString("user_last_name", "");
        txtUserFirstLastName.setText(firstLastName);
        txtEmail.setText(firebaseUser.getEmail());


        filtersStr = new ArrayList<String>();

        // Location
        int radius = sharedPreferencesSettings.getInt("radius", -1);

        // -- location --
        // check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // Ask for permissions
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 99);

        }else{
            // permissions already accepted keep requesting the location
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        // ----

        // Education levels
        boolean elementary = sharedPreferencesSettings.getBoolean("elementary_sp", true);
        boolean middle = sharedPreferencesSettings.getBoolean("middle_sp", true);
        boolean high = sharedPreferencesSettings.getBoolean("high_sp", true);
        boolean collage = sharedPreferencesSettings.getBoolean("collage_sp", true);
        boolean master = sharedPreferencesSettings.getBoolean("masters_sp", true);
        boolean doctor = sharedPreferencesSettings.getBoolean("doctors_sp", true);

        if (elementary){
            filtersStr.add(getString(R.string.elementary_school_title));
        }
        if(middle){
            filtersStr.add(getString(R.string.middle_school_title));
        }
        if(high){
            filtersStr.add(getString(R.string.high_school_title));
        }
        if (collage){
            filtersStr.add(getString(R.string.collage_title));
        }
        if(master){
            filtersStr.add(getString(R.string.masters_title));
        }
        if(doctor){
            filtersStr.add(getString(R.string.doctors_title));
        }

        // Categories
        boolean math = sharedPreferencesSettings.getBoolean("math_sp", true);
        boolean sport = sharedPreferencesSettings.getBoolean("sport_sp", true);
        boolean english = sharedPreferencesSettings.getBoolean("english_sp", true);
        boolean other = sharedPreferencesSettings.getBoolean("other_sp", true);

        if (math){
            filtersStr.add(getString(R.string.mathematics_title));
        }
        if(sport){
            filtersStr.add(getString(R.string.sport_title));
        }
        if(english){
            filtersStr.add(getString(R.string.english_title));
        }
        if (other){
            filtersStr.add(getString(R.string.other_title));
        }

        FilterWrapper filterWrapper = new FilterWrapper(filtersStr);

        new FilterTask(radius,filterWrapper).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop checking for location while paused
        locationManager.removeUpdates(locationListener);
    }

    private class FilterTask extends AsyncTask<String,Void, List<Question>>{
        private int radius;
        private FilterWrapper filters;

        public FilterTask(int radius, FilterWrapper filterWrapper){
            this.radius=radius;
            this.filters=filterWrapper;
        }

        @Override
        protected List<Question> doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RedditAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RedditAPI client = retrofit.create(RedditAPI.class);
            Call<List<Question>> questionResponse = client.findByFilters(radius,filters);

            questionResponse.enqueue(new Callback<List<Question>>() {
                @Override
                public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                    List<Question> questionListResponse = response.body();
                    Log.w(TAG, "questionListResponse.size()" + questionListResponse.size());

                    for(Question q:questionListResponse) {
                        Question newQuestion = new Question();
                        newQuestion.setOwner(q.getOwner());
                        newQuestion.setDescription(q.getDescription());
                        newQuestion.setCategory(q.getCategory());

                        newQuestion.setAnswer1(q.getAnswer1());
                        newQuestion.setAnswer2(q.getAnswer2());
                        newQuestion.setAnswer3(q.getAnswer3());
                        newQuestion.setAnswer4(q.getAnswer4());
                        newQuestion.setEduLevel(q.getEduLevel());
                        newQuestion.setCorrectAns(q.getCorrectAns());
                        newQuestion.setAnswered(q.getAnswered());
                        newQuestion.setId(q.getId());


                        if (!questionList.contains(newQuestion)) {
                            questionList.add(newQuestion);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Question>> call, Throwable t) {
                    Log.e(TAG, "Something went wrong");
                }
            });
            return new ArrayList<>();
        }
    }
}
