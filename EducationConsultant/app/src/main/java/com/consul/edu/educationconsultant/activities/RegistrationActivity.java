package com.consul.edu.educationconsultant.activities;

import android.content.Intent;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private Button btnSignup;
    private TextView linkLogin;
    private EditText inputFirstname;
    private EditText inputLastname;
    private EditText inputEmail;
    private EditText inputPassword;

    private FrameLayout frameProgressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        frameProgressBar = (FrameLayout) findViewById(R.id.frame_progress_bar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
    }

    /**
     * Called when a signup button has been clicked.
     *
     * @param view The view (in this case a button) that was clicked.
     *
     * */
    public void onClickSignUpBtn(View view){
        inputFirstname = (EditText) findViewById(R.id.firstname);
        inputLastname = (EditText) findViewById(R.id.lastname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        btnSignup = (Button) findViewById(R.id.btn_signup);
        if(btnSignup != null) {
            String firstname = inputFirstname.getText().toString().trim();
            String lastname = inputLastname.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            // Check if the first name field is empty
            if (TextUtils.isEmpty(firstname)) {
                Toast.makeText(getApplicationContext(), R.string.msg_enter_firstname, Toast.LENGTH_LONG).show();
                return;
            }

            // Check if the last name field is empty
            if (TextUtils.isEmpty(lastname)) {
                Toast.makeText(getApplicationContext(), R.string.msg_enter_lastname, Toast.LENGTH_LONG).show();
                return;
            }

            // Check if the email field is empty
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), R.string.msg_enter_email, Toast.LENGTH_LONG).show();
                return;
            }

            // Check if the password field is empty
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), R.string.msg_enter_password, Toast.LENGTH_LONG).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), R.string.msg_password_length, Toast.LENGTH_LONG).show();
                return;
            }

            frameProgressBar.setVisibility(View.VISIBLE);

            // create user
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(RegistrationActivity.this, "createUserWithEmail: " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            frameProgressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Authentication failed. " + task.getException(),
                                        Toast.LENGTH_LONG).show();
                            } else {
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
        linkLogin = (TextView) findViewById(R.id.link_login);
        if (linkLogin != null ){
            // Start the Login activity
            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(i);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        frameProgressBar.setVisibility(View.GONE);
    }
}
