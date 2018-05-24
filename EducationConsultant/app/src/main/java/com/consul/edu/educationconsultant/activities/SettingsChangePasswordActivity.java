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
import com.consul.edu.educationconsultant.asyncTasks.UserUpdatePasswordTask;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsChangePasswordActivity extends AppCompatActivity {

    private EditText inputOldPassword;
    private EditText inputNewPassword;
    private EditText inputNewPasswordRepeat;

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
        setContentView(R.layout.activity_settings_change_password);

        toolbar = (Toolbar) findViewById(R.id.toolbar_change_pass);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        // getSupportActionBar: using the toolbar from the Support Library
        actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        inputOldPassword = (EditText) findViewById(R.id.old_password);
        inputNewPassword = (EditText) findViewById(R.id.new_password);
        inputNewPasswordRepeat = (EditText) findViewById(R.id.new_password_repeat);

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

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);
    }

    /**
     * This method runs when the app bar’s menu gets created.
     *
     * @param menu Java representation of the menu resource file
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds items to the app bar
        getMenuInflater().inflate(R.menu.menu_change_password,menu);
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
            case R.id.action_change_password:
                sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);

                String oldPasswordStr = inputOldPassword.getText().toString();
                String newPasswordStr = inputNewPassword.getText().toString();
                String newPasswordRepeatStr = inputNewPasswordRepeat.getText().toString();

                // Check if the password field is empty
                if (TextUtils.isEmpty(oldPasswordStr)) {
                    Toast.makeText(getApplicationContext(), R.string.old_password, Toast.LENGTH_LONG).show();
                    return false;
                }

                if (TextUtils.isEmpty(newPasswordStr)) {
                    Toast.makeText(getApplicationContext(), R.string.new_password, Toast.LENGTH_LONG).show();
                    return false;
                }

                if (newPasswordStr.length() < 6) {
                    Toast.makeText(getApplicationContext(), R.string.msg_password_length, Toast.LENGTH_LONG).show();
                    return false;
                }

                if(sharedPreferences.getString("user_password", "").equals(oldPasswordStr)){
                    if(newPasswordStr.equals(newPasswordRepeatStr)){
                        Long userId = sharedPreferences.getLong("user_id", -1L);
                        new UserUpdatePasswordTask().execute(userId.toString(), newPasswordStr);

                        firebaseUser.updatePassword(newPasswordStr);
                        // Re-authenticate a user
                        // Get auth credentials from the user for re-authentication.
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(sharedPreferences.getString("user_email", ""), newPasswordStr);
                        // Prompt the user to re-provide their sign-in credentials
                        firebaseUser.reauthenticate(credential);

                        final SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_password",newPasswordStr);
                        editor.apply();

                        Toast toast = Toast.makeText(this,R.string.password_changed,Toast.LENGTH_SHORT);
                        toast.show();

                        Intent intent = new Intent(SettingsChangePasswordActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        finish();
                        // Returning true tells Android you're dealt with the item being clicked
                        return true;
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.passwords_do_not_match, Toast.LENGTH_LONG).show();
                        return false;
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.old_password_error, Toast.LENGTH_LONG).show();
                    return false;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
