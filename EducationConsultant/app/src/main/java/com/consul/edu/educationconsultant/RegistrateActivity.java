package com.consul.edu.educationconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrateActivity extends AppCompatActivity {

    private Button btnSignup;
    private TextView linkLogin;
    private EditText inputFirstname;
    private EditText inputLastname;
    private EditText inputEmail;
    private EditText inputPassword;

    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        inputFirstname = (EditText) findViewById(R.id.firstname);
        inputLastname = (EditText) findViewById(R.id.lastname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // SIGNUP button
        btnSignup = (Button) findViewById(R.id.btn_signup);
        if(btnSignup != null) {
            btnSignup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String firstname = inputFirstname.getText().toString().trim();
                    String lastname = inputLastname.getText().toString().trim();
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();

                    // validate firstname input
                    if (TextUtils.isEmpty(firstname)) {
                        Toast.makeText(getApplicationContext(), R.string.msg_enter_firstname, Toast.LENGTH_LONG).show();
                        return;
                    }

                    // validate lastname input
                    if (TextUtils.isEmpty(lastname)) {
                        Toast.makeText(getApplicationContext(), R.string.msg_enter_lastname, Toast.LENGTH_LONG).show();
                        return;
                    }

                    // validate email input
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), R.string.msg_enter_email, Toast.LENGTH_LONG).show();
                        return;
                    }

                    // validate password input
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), R.string.msg_enter_password, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), R.string.msg_password_length, Toast.LENGTH_LONG).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    //create user
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegistrateActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            Toast.makeText(RegistrateActivity.this, "createUserWithEmail: " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Authentication failed. " + task.getException(),
                                                        Toast.LENGTH_LONG).show();
                                            } else {
                                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(i);
                                                finish();
                                            }


                                        }
                                    });

                                    //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    //startActivity(i);
                }
            });
        }

        // Login link
        linkLogin = (TextView) findViewById(R.id.link_login);
        if (linkLogin != null ){
            linkLogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
