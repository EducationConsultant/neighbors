package com.consul.edu.educationconsultant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.consul.edu.educationconsultant.activities.NavigationDrawerActivity;
import com.consul.edu.educationconsultant.activities.RegistrationActivity;
import com.consul.edu.educationconsultant.activities.ResetPasswordActivity;
import com.consul.edu.educationconsultant.asyncTasks.RegistrationTask;
import com.consul.edu.educationconsultant.asyncTasks.UserFindByEmailTask;
import com.consul.edu.educationconsultant.asyncTasks.UserGoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity{

    private Button btnLogin;
    private Button btnSignup;
    private Button btnGoogleSignIn;
    private TextView linkForgotPass;
    private EditText inputEmail;
    private EditText inputPassword;

    private FrameLayout frameProgressBar;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 2;

    private String sharedPrefName;
    private SharedPreferences sharedPreferences;

    private Handler handler;
    private int state;

    /**
     * This method gets called immediately after your activity is launched.
     * This method is where you do all your normal activity setup such as calling setContentView().
     *
     * You should always override this method. If you don’t override it, you won’t be able to tell Android what layout your activity should use.
     * At this point, the activity isn’t yet visible.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get views
        frameProgressBar = (FrameLayout) findViewById(R.id.frame_progress_bar);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnGoogleSignIn = (Button) findViewById(R.id.btn_google_sign_in);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        handler= new Handler();
        state = 0;

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    /**
     * This method gets called when the activity is about to become visible.
     * After the onStart() method has run, the user can see the activity on the screen.
     * */
    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = auth.getCurrentUser();
        sharedPrefName = "currentUser";

        // Check if a user is logged in
        if (firebaseUser != null) {
            // User is logged in
            Intent i = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);

        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                final GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                auth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //new UserGoogleSignIn(sharedPreferences).execute(account.getGivenName(),account.getFamilyName(),account.getEmail());
                                    new RegistrationTask(sharedPreferences).execute(account.getGivenName(),account.getFamilyName(),account.getEmail(),"");
                                    // Sign in success, update UI with the signed-in user's information
                                    firebaseUser = auth.getCurrentUser();

                                    // Signed in successfully, show authenticated UI.
                                    // Start the Main activity
                                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, getString(R.string.google_failed), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Toast.makeText(LoginActivity.this, getString(R.string.google_failed), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Called when a login button has been clicked.
     *
     * @param view The view (in this case a button) that was clicked.
     *
     * */
    public void onClickLogin(View view){
        if (btnLogin != null) {
            final String emailStr = inputEmail.getEditableText().toString().trim();
            final String passwordStr = inputPassword.getEditableText().toString().trim();

            // Check if the email field is empty
            if (TextUtils.isEmpty(emailStr)) {
                Toast.makeText(getApplicationContext(), R.string.msg_enter_email, Toast.LENGTH_LONG).show();
                return;
            }

            // Check if the passwordStr field is empty
            if (TextUtils.isEmpty(passwordStr)) {
                Toast.makeText(getApplicationContext(), R.string.msg_enter_password, Toast.LENGTH_LONG).show();
                return;
            }

            frameProgressBar.setVisibility(View.VISIBLE);



            // Authenticate user
            auth.signInWithEmailAndPassword(emailStr, passwordStr)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    frameProgressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        //frameProgressBar.setVisibility(View.GONE);
                                        // there was an error
                                        Toast.makeText(LoginActivity.this, getString(R.string.msg_authentication_failed), Toast.LENGTH_LONG).show();
                                    } else {
                                        new UserFindByEmailTask(sharedPreferences).execute(emailStr);

                                        // Start the Main activity
                                       Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                                       startActivity(intent);
                                       finish();
                                    }
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
