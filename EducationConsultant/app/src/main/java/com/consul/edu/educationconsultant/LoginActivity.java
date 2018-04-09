package com.consul.edu.educationconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

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

        // LOGIN button
        btnLogin = (Button) findViewById(R.id.btn_login);
        if (btnLogin != null) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
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
                    Intent i = new Intent(getApplicationContext(), ForgotPassActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}
