package com.consul.edu.educationconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity{

    private FirebaseAuth auth;

    // The view objects
    private Button btnLogin;
    private Button btnSignup;
    private TextView linkForgotPass;

    private EditText inputEmail;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        //initializing EditText view objects
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        final String email = inputEmail.getEditableText().toString().trim();
        final String password = inputPassword.getEditableText().toString().trim();


        // LOGIN button
        btnLogin = (Button) findViewById(R.id.btn_login);
        if (btnLogin != null) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
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

                    // Start the Main activity
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            });
        }

        // SIGNUP button
        btnSignup = (Button) findViewById(R.id.btn_signup);
        if (btnSignup != null) {
            btnSignup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Start the Registrate activity
                    Intent i = new Intent(getApplicationContext(), RegistrateActivity.class);
                    startActivity(i);
                }
            });
        }

        // FORGOT PASSWORD link
        linkForgotPass = (TextView) findViewById(R.id.link_forgot_pass);
        if (linkForgotPass != null) {
            linkForgotPass.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Start the ForgotPass activity
                    Intent i = new Intent(getApplicationContext(), ForgotPassActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}
