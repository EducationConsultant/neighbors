package com.consul.edu.educationconsultant.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.databaseHelpers.UserDatabaseHelper;
import com.consul.edu.educationconsultant.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * The AppCompatActivity class is a subclass of Activity.
 * It lives in the AppCompat Support Library, and it’s designed to work with the AppCompat themes.
 * */
public class ProfileActivity extends AppCompatActivity {
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputEmail;
    private Button btnResetPassword;

    private Toolbar toolbar;
    private ActionBar actionBar;

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
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        // getSupportActionBar: using the toolbar from the Support Library
        actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        inputFirstName = (EditText) findViewById(R.id.firstname_edit);
        inputLastName = (EditText) findViewById(R.id.lastname_edit);
        inputEmail = (EditText) findViewById(R.id.email_edit);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_pass);

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

        // Get a reference to the SQLite helper
        userDatabaseHelper = new UserDatabaseHelper(this);

        // If Android can’t get a reference to the database and a SQLiteException is thrown, we’ll use a Toast to tell the user that the database is unavailable
        try {
            // Get a reference to the database
            db = userDatabaseHelper.getWritableDatabase();

            /**
             * Put user data in fields.
             * Cursor lets you read from and write to the database.
             *
             * First parameter: Name of the table.
             * Second parameter: We want to return the values of this columns.
             * Third parameter: Specifies the column in the condition. We want the value in the EMAIL column to be equal to some value, and the ? symbol is a placeholder for this value.
             * Fourth parameter: An array of Strings that specifies what the value of the condition should be.
             *
             */
            cursor = db.query("USER",
                    new String[]{"FIRST_NAME","LAST_NAME","EMAIL"},
                    "EMAIL = ?",
                    new String[] {firebaseUser.getEmail()},
                    null,null,null);

            /**
             * To go to the first record in a cursor.
             * This method returns a value of true if it finds a record, and false if the cursor hasn’t returned any records.
             * */
            if(cursor.moveToFirst()){
                inputFirstName.setText(cursor.getString(cursor.getColumnIndex("FIRST_NAME")));
                inputLastName.setText(cursor.getString(cursor.getColumnIndex("LAST_NAME")));
                inputEmail.setText(cursor.getString(cursor.getColumnIndex("EMAIL")));
            }else{
                String currentString = firebaseUser.getDisplayName();
                String[] separated = currentString.split(" ");
                String firstName = separated[0];
                String lastName = separated[1];

                insertUser(db, firstName, lastName, firebaseUser.getEmail(),"");

                inputFirstName.setText(firstName);
                inputLastName.setText(lastName);
                inputEmail.setText(firebaseUser.getEmail());
            }
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
        cursor.close();
        db.close();
    }

    /**
     * This method runs when the app bar’s menu gets created.
     *
     * @param menu Java representation of the menu resource file
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds items to the app bar
        getMenuInflater().inflate(R.menu.menu_profile,menu);
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
            case R.id.action_edit_profile:
                updateUser(db,firebaseUser.getEmail(),inputFirstName.getText().toString(),inputLastName.getText().toString(),inputEmail.getText().toString());

                // Update a user's basic profile information
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(inputFirstName.getText().toString() + " " + inputLastName.getText().toString())
                        .build();
                firebaseUser.updateProfile(profileUpdates);
                firebaseUser.updateEmail(inputEmail.getText().toString());

                Toast toast = Toast.makeText(this,R.string.msg_profile_updated,Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(ProfileActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
                finish();
                // Returning true tells Android you're dealt with the item being clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickResetPassword(View view){
        if (btnResetPassword != null) {
            auth.sendPasswordResetEmail(firebaseUser.getEmail())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, R.string.msg_send_instructions, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ProfileActivity.this, R.string.msg_failed_send, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }

    private void updateUser(SQLiteDatabase db, String oldEmail, String firstName, String lastName, String email){
        // ContentValues object describes a set of data.
        // ContentValues object that specifies what you want to update values to
        ContentValues userValues = new ContentValues();

        // Change the value of the FIRST_NAME column to firstName value
        userValues.put("FIRST_NAME", firstName);
        userValues.put("LAST_NAME", lastName);
        userValues.put("EMAIL", email);

        /**
         * This method updates records in the database, and returns the number of records it’s updated.
         *
         * The first parameter is the name of the table you want to update.
         * The second parameter is the ContentValues object that describes the values you want to update.
         * The last two parameters specify which records you want to update by describing conditions for the update. Together, they form the WHERE clause of a SQL statement.
         *
         * The third parameter specifies the name of the column in the condition.
         * "EMAIL = ?"; it means that we want the value in the EMAIL column to be equal to some value. The ? symbol is a placeholder for this value.
         *
         * The last parameter is an array of Strings that says what the value of the condition should be.
         * */
        db.update("USER", userValues, "EMAIL = ?", new String[]{oldEmail});
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
