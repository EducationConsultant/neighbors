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

public class SettingsCategoryActivity extends AppCompatActivity {

    private Switch mathematics, sport, english, other;
    private SharedPreferences sharedPreferences;
    private String sharedPrefName;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_category);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        mathematics = (Switch) findViewById(R.id.mathematics);
        sport = (Switch) findViewById(R.id.sport);
        english = (Switch) findViewById(R.id.english);
        other = (Switch) findViewById(R.id.other);

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

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);

        mathematics.setChecked(sharedPreferences.getBoolean("math_sp", true));
        sport.setChecked(sharedPreferences.getBoolean("sport_sp", true));
        english.setChecked(sharedPreferences.getBoolean("english_sp", true));
        other.setChecked(sharedPreferences.getBoolean("other_sp", true));
    }

    public void onClickSwitchCategory(View view) {
        boolean checked = ((Switch) view).isChecked();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (view.getId()) {
            case R.id.mathematics:
                editor.putBoolean("math_sp",checked);
                editor.apply();
                break;
            case R.id.english:
                editor.putBoolean("english_sp",checked);
                editor.apply();
                break;
            case R.id.sport:
                editor.putBoolean("sport_sp",checked);
                editor.apply();
                break;
            case R.id.other:
                editor.putBoolean("other_sp",checked);
                editor.apply();
                break;
            default:
                Toast.makeText(SettingsCategoryActivity.this, "Default statement", Toast.LENGTH_SHORT).show();

        }
    }
}
