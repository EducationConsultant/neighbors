package com.consul.edu.educationconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegistrateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        // SIGNUP button
        final Button btnSignup = (Button) findViewById(R.id.btn_signup);
        if(btnSignup != null) {
            btnSignup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            });
        }

        // Login link
        final TextView linkLogin = (TextView) findViewById(R.id.link_login);
        if (linkLogin != null ){
            linkLogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    
                }
            });
        }
    }
}
