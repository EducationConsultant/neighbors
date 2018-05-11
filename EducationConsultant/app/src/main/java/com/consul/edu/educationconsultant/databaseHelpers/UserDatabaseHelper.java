package com.consul.edu.educationconsultant.databaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "educon_db";
    private static final int DB_VERSION = 1;

    public UserDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * This method gets called when the database first gets created. Here we are using it to create the table.
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "FIRST_NAME TEXT, "
                    + "LAST_NAME TEXT, "
                    + "EMAIL TEXT, "
                    + "PASSWORD TEXT);");
        //insertUser(db,"Petar","Miric","pera@gmail.com","pera123");
      // db.execSQL("DROP TABLE USER;");
    }

    /**
     * This method gets called when the database needs to be upgraded.
     *
     * @param oldVersion The user's version of the database, which is put of day
     * @param newVersion The new version described in the SQLite helper code
     * */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
