package com.consul.edu.educationconsultant.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsEducationLevelsActivity extends AppCompatActivity {
    private Switch elementarySchool;
    private Switch middleSchool;
    private Switch highSchool;
    private Switch collage;
    private Switch masters;
    private Switch doctors;

    private Toolbar toolbar;
    private ActionBar actionBar;

    private SharedPreferences sharedPreferences;
    private String sharedPrefName;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    /**
     *
     * This method gets called immediately after your activity is launched.
     * This method is where you do all your normal activity setup such as calling setContentView().
     *
     * You should always override this method. If you don’t override it, you won’t be able to tell Android what layout your activity should use.
     * At this point, the activity isn’t yet visible.
     *
     * @param  savedInstanceState If the activity’s being created from scratch, this parameter will be null.
     *                            If the activity’s being recreated and there’s been a prior call to onSaveInstanceState(), the Bundle object used by onSaveInstanceState() will get passed to the activity.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_education_levels);

        elementarySchool = (Switch) findViewById(R.id.elementary_school);
        middleSchool = (Switch) findViewById(R.id.middle_school);
        highSchool = (Switch) findViewById(R.id.high_school);
        collage = (Switch) findViewById(R.id.collage);
        masters = (Switch) findViewById(R.id.masters);
        doctors = (Switch)findViewById(R.id.doctors);

        toolbar = (Toolbar) findViewById(R.id.toolbar_edu_level);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        // getSupportActionBar: using the toolbar from the Support Library
        actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
    }

    /**
     *
     * This method gets called when the activity is about to become visible.
     * After the onStart() method has run, the user can see the activity on the screen.
     *
     * */
    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = auth.getCurrentUser();
        sharedPrefName = "Settings" + "_" + firebaseUser.getEmail();
    }

    /**
     *
     * This method gets called when the activity is started or resumed.
     * After the onResume() method has run, the activity has the focus and the user can interact with it.
     *
     * */
    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);

        elementarySchool.setChecked(sharedPreferences.getBoolean("elementary_sp", true));
        middleSchool.setChecked(sharedPreferences.getBoolean("middle_sp", true));
        highSchool.setChecked(sharedPreferences.getBoolean("high_sp", true));
        collage.setChecked(sharedPreferences.getBoolean("collage_sp", true));
        masters.setChecked(sharedPreferences.getBoolean("masters_sp", true));
        doctors.setChecked(sharedPreferences.getBoolean("doctors_sp", true));
    }

    /**
     * Called when the switch is clicked.
     *
     * @param view The view (in this case a switch) that was clicked.
     * */
    public void onClickSwitch(View view){
        boolean checked = ((Switch) view).isChecked();

        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (view.getId()){
            case R.id.elementary_school:
                editor.putBoolean("elementary_sp",checked);
                editor.apply();
                break;
            case R.id.middle_school:
                editor.putBoolean("middle_sp",checked);
                editor.apply();
                break;
            case R.id.high_school:
                editor.putBoolean("high_sp",checked);
                editor.apply();
                break;
            case R.id.collage:
                editor.putBoolean("collage_sp",checked);
                editor.apply();
                break;
            case R.id.masters:
                editor.putBoolean("masters_sp",checked);
                editor.apply();
                break;
            case R.id.doctors:
                editor.putBoolean("doctors_sp",checked);
                editor.apply();
                break;
            default:
                Toast.makeText(SettingsEducationLevelsActivity.this, "Default statement", Toast.LENGTH_SHORT).show();

        }
    }
}
