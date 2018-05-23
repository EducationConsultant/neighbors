package com.consul.edu.educationconsultant.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.consul.edu.educationconsultant.LoginActivity;
import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.asyncTasks.RegistrationUserTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {
    private Button btnSignup;
    private TextView linkLogin;
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputEmail;
    private EditText inputPassword;

    private FrameLayout frameProgressBar;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    private String sharedPrefName;
    private SharedPreferences sharedPreferences;

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
        setContentView(R.layout.activity_registration);

        // Get views
        frameProgressBar = (FrameLayout) findViewById(R.id.frame_progress_bar);
        inputFirstName = (EditText) findViewById(R.id.firstname);
        inputLastName = (EditText) findViewById(R.id.lastname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        linkLogin = (TextView) findViewById(R.id.link_login);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

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
        frameProgressBar.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);
    }

    /**
     * Called when a signup button has been clicked.
     *
     * @param view The view (in this case a button) that was clicked.
     *
     * */
    public void onClickSignUpBtn(View view){
        if(btnSignup != null) {
            final String firstNameStr = inputFirstName.getText().toString().trim();
            final String lastNameStr = inputLastName.getText().toString().trim();
            final String emailStr = inputEmail.getText().toString().trim();
            final String passwordStr = inputPassword.getText().toString().trim();

            // Check if the first name field is empty
            if (TextUtils.isEmpty(firstNameStr)) {
                Toast.makeText(getApplicationContext(), R.string.msg_enter_firstname, Toast.LENGTH_LONG).show();
                return;
            }

            // Check if the last name field is empty
            if (TextUtils.isEmpty(lastNameStr)) {
                Toast.makeText(getApplicationContext(), R.string.msg_enter_lastname, Toast.LENGTH_LONG).show();
                return;
            }

            // Check if the email field is empty
            if (TextUtils.isEmpty(emailStr)) {
                Toast.makeText(getApplicationContext(), R.string.msg_enter_email, Toast.LENGTH_LONG).show();
                return;
            }

            // Check if the password field is empty
            if (TextUtils.isEmpty(passwordStr)) {
                Toast.makeText(getApplicationContext(), R.string.msg_enter_password, Toast.LENGTH_LONG).show();
                return;
            }

            if (passwordStr.length() < 6) {
                Toast.makeText(getApplicationContext(), R.string.msg_password_length, Toast.LENGTH_LONG).show();
                return;
            }

            frameProgressBar.setVisibility(View.VISIBLE);

            // Firebase auth
            // create user
            auth.createUserWithEmailAndPassword(emailStr, passwordStr)
                    .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            frameProgressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_LONG).show();
                            } else {
                                new RegistrationUserTask(sharedPreferences).execute(firstNameStr,lastNameStr,emailStr,passwordStr);

                                firebaseUser = auth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(firstNameStr + " " + lastNameStr)
                                        .build();
                                firebaseUser.updateProfile(profileUpdates);

                                Intent i = new Intent(RegistrationActivity.this, NavigationDrawerActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    });
        }

    }

    /**
     * Called when a login link has been clicked.
     *
     * @param view The view (in this case a TextView) that was clicked.
     *
     * */
    public void onClickLoginLink(View view){
        if (linkLogin != null ){
            // Start the Login activity
            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}
