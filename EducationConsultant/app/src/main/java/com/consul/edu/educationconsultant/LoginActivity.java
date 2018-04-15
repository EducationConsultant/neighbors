package com.consul.edu.educationconsultant;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity{

    private Button btnLogin;
    private Button btnSignup;
    private TextView linkForgotPass;
    private EditText inputEmail;
    private EditText inputPassword;

    private FrameLayout frameProgressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Check if a user is logged in
        // TODO: Uncomment this part when development is finished
        /*if (auth.getCurrentUser() != null) {
            // User is logged in
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }*/

        setContentView(R.layout.activity_login);

        frameProgressBar = (FrameLayout) findViewById(R.id.frame_progress_bar);
    }

    /**
     * Called when a login button has been clicked.
     *
     * @param view The view (in this case a button) that was clicked.
     *
     * */
    public void onClickLogin(View view){

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        if (btnLogin != null) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String email = inputEmail.getEditableText().toString().trim();
                    final String password = inputPassword.getEditableText().toString().trim();

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

                    frameProgressBar.setVisibility(View.VISIBLE);

                    // Start the Main activity
                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                    finish();

                    // Authenticate user
                    // TODO: Uncomment this part when development is finished
                    /*auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            frameProgressBar.setVisibility(View.GONE);
                                            if (!task.isSuccessful()) {
                                                // there was an error
                                                Toast.makeText(LoginActivity.this, getString(R.string.msg_authentication_failed), Toast.LENGTH_LONG).show();

                                            } else {
                                                // Start the Main activity
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });*/
                }
            });
        }
    }

    /**
     * Called when a signup button has been clicked.
     *
     * @param view The view (in this case a button) that was clicked.
     *
     * */
    public void onClickSignUp(View view){
        btnSignup = (Button) findViewById(R.id.btn_signup);
        if (btnSignup != null) {
            // Start the Registration activity
            Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(i);
        }
    }

    /**
     * Called when a forgot password link has been clicked.
     *
     * @param view The view (in this case a TextView) that was clicked.
     *
     * */
    public void onClickResetPassword(View view){
        linkForgotPass = (TextView) findViewById(R.id.link_forgot_pass);
        if (linkForgotPass != null) {
            // Start the ResetPassword activity
            Intent i = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(i);
        }
    }

}
