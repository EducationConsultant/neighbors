package com.consul.edu.educationconsultant.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.databaseHelpers.UserDatabaseHelper;
import com.consul.edu.educationconsultant.model.User;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The AppCompatActivity class is a subclass of Activity.
 * It lives in the AppCompat Support Library, and it’s designed to work with the AppCompat themes.
 * */
public class ProfileActivity extends AppCompatActivity {
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputEmail;
    private EditText inputPassword;

    private FloatingActionButton fbtnEditProfile;

    private FirebaseAuth auth;
    private User user;

    private SQLiteOpenHelper userDatabaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        inputFirstName = (EditText) findViewById(R.id.firstname_edit);
        inputLastName = (EditText) findViewById(R.id.lastname_edit);
        inputEmail = (EditText) findViewById(R.id.email_edit);
        inputPassword = (EditText) findViewById(R.id.password_edit);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

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
                    new String[]{"FIRST_NAME","LAST_NAME","EMAIL","PASSWORD"},
                    "EMAIL = ?",
                    new String[] {auth.getCurrentUser().getEmail()},
                    null,null,null);

            /**
             * To go to the first record in a cursor.
             * This method returns a value of true if it finds a record, and false if the cursor hasn’t returned any records.
             * */
            // TODO:Ne pronadje user-a!!!!!
            if(cursor.moveToFirst()){
                user = new User();
                user.setFirstName(cursor.getString(cursor.getColumnIndex("FIRST_NAME")));

                inputFirstName.setText(user.getFirstName());

                Toast toast = Toast.makeText(this,"FirstName: " + user.getFirstName(),Toast.LENGTH_SHORT);
                toast.show();
            }
        }catch(SQLiteException e){
            Toast toast = Toast.makeText(this,R.string.db_unavailable,Toast.LENGTH_SHORT);
            toast.show();
        }
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
                // TODO: Implement Edit Profile
                Intent intent = new Intent(ProfileActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
                // Returning true tells Android you're dealt with the item being clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
