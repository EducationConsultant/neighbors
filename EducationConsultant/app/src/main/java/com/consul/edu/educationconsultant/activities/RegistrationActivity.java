package com.consul.edu.educationconsultant.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
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
import com.consul.edu.educationconsultant.databaseHelpers.UserDatabaseHelper;
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

    private SQLiteOpenHelper userDatabaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

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

        // Get a reference to the SQLite helper
        userDatabaseHelper = new UserDatabaseHelper(this);

        // If Android can’t get a reference to the database and a SQLiteException is thrown, we’ll use a Toast to tell the user that the database is unavailable
        try {
            // Get a reference to the database
            db = userDatabaseHelper.getWritableDatabase();
        }catch(SQLiteException e){
            Toast toast = Toast.makeText(this,R.string.db_unavailable,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     *
     * This method is the final call you get before the activity is destroyed.
     * For example, if it’s been told to finish, if the activity is being recreated due to a change in device configuration, or if Android has decided to destroy the activity in order to save space.
     *
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
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
            String emailStr = inputEmail.getText().toString().trim();
            String passwordStr = inputPassword.getText().toString().trim();

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

            // Check if the email is unique
            /**
             * Cursor lets you read from and write to the database.
             *
             * First parameter: Name of the table.
             * Second parameter: We want to return the values of this columns.
             * Third parameter: Specifies the column in the condition. We want the value in the EMAIL column to be equal to some value, and the ? symbol is a placeholder for this value.
             * Fourth parameter: An array of Strings that specifies what the value of the condition should be.
             *
             */
            cursor = db.query("USER",
                    new String[]{"EMAIL"},
                    "EMAIL = ?",
                    new String[] {emailStr},
                    null,null,null);

            /**
             * To go to the first record in a cursor.
             * This method returns a value of true if it finds a record, and false if the cursor hasn’t returned any records.
             * */
            if(cursor.moveToFirst()){
                Toast toast = Toast.makeText(this,R.string.msg_email_unique,Toast.LENGTH_SHORT);
                toast.show();
                cursor.close();
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

            // create user in database
            insertUser(db,firstNameStr,lastNameStr,emailStr,passwordStr);

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

    private void insertUser(SQLiteDatabase db, String firstName, String lastName, String email, String password){
        // ContentValues object describes a set of data.
        // You usually create a new ContentValues object for each row of data you want to create.
        ContentValues userValues = new ContentValues();

        // Add data to the ContentValues object
        // FIRST_NAME is the column you want to add data to, and firstName is the data
        userValues.put("FIRST_NAME", firstName);
        userValues.put("LAST_NAME", lastName);
        userValues.put("EMAIL", email);
        userValues.put("PASSWORD", password);

        /**
         * This method inserts data into a table, and returns the ID of the record once it’s been inserted.
         * If the method is unable to insert the record, it returns a value of -1.
         *
         * The middle parameter is usually set to null.
         * It’s there in case the ContentValues object is empty, and you want to insert an empty row into your table.
         * It’s unlikely you’d want to do this, but if you did you’d replace the null value with the name of one of the columns in your table.
         * */
        db.insert("USER", null, userValues);
    }
}
