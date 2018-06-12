package com.consul.edu.educationconsultant.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.asyncTasks.UserUpdateProfileTask;
import com.consul.edu.educationconsultant.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * The AppCompatActivity class is a subclass of Activity.
 * It lives in the AppCompat Support Library, and it’s designed to work with the AppCompat themes.
 * */
public class ProfileActivity extends AppCompatActivity {
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputEmail;

    private Toolbar toolbar;
    private ActionBar actionBar;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    private SharedPreferences sharedPreferences;
    private String sharedPrefName;

    /**
     *
     * This method gets called immediately after your activity is launched.
     * This method is where you do all your normal activity setup such as calling setContentView().
     *
     * You should always override this method. If you don’t override it, you won’t be able to tell Android what layout your activity should use.
     * At this point, the activity isn’t yet visible.
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        // getSupportActionBar: using the toolbar from the Support Library
        actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        inputFirstName = (EditText) findViewById(R.id.firstname_edit);
        inputLastName = (EditText) findViewById(R.id.lastname_edit);
        inputEmail = (EditText) findViewById(R.id.email_edit);

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
        sharedPrefName = "currentUser";
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

        inputFirstName.setText(sharedPreferences.getString("user_first_name", ""));
        inputLastName.setText(sharedPreferences.getString("user_last_name", ""));
        inputEmail.setText(sharedPreferences.getString("user_email", ""));
    }

    /**
     * This method runs when the app bar’s menu gets created.
     *
     * @param menu Java representation of the menu resource file
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds items to the app bar
        getMenuInflater().inflate(R.menu.menu_profile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Implementing this method makes activity react when an action in the app bar is clicked.
     *
     * @param item Represents the action on the app bar that was clicked
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit_profile:
                // Check if the first name field is empty
                if (TextUtils.isEmpty(inputFirstName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), R.string.msg_enter_firstname, Toast.LENGTH_LONG).show();
                    return false;
                }

                // Check if the last name field is empty
                if (TextUtils.isEmpty(inputLastName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), R.string.msg_enter_lastname, Toast.LENGTH_LONG).show();
                    return false;
                }

                // Check if the email field is empty
                if (TextUtils.isEmpty(inputEmail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), R.string.msg_enter_email, Toast.LENGTH_LONG).show();
                    return false;
                }


                Long userId = sharedPreferences.getLong("user_id", -1L);
                new UserUpdateProfileTask(sharedPreferences).execute(userId.toString(), inputFirstName.getText().toString(), inputLastName.getText().toString(), inputEmail.getText().toString());

                // Update a user's basic profile information
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(inputFirstName.getText().toString() + " " + inputLastName.getText().toString())
                        .build();
                firebaseUser.updateProfile(profileUpdates);
                firebaseUser.updateEmail(inputEmail.getText().toString());

                User userToUpdate = new User();
                userToUpdate.setFirstName(inputFirstName.getText().toString());
                userToUpdate.setLastName(inputLastName.getText().toString());
                userToUpdate.setEmail(inputEmail.getText().toString());

                Toast toast = Toast.makeText(this,R.string.msg_profile_updated,Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(ProfileActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
                finish();
                // Returning true tells Android you're dealt with the item being clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
