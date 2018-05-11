package com.consul.edu.educationconsultant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.SQLInput;

/**
 * Created by Svetlana on 5/10/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="educon.db";
    public static final String TABLE_NAME="question_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "TITLE";
    public static final String COL3 = "USERNAME";
    public static final String COL4 = "DESCRIPTION";
    public static final String COL5 = "CATEGORY";
    public static final String COL6 = "ANSWER1";
    public static final String COL7 = "ANSWER2";
    public static final String COL8 = "ANSWER3";
    public static final String COL9 = "ANSWER4";
    public static final String COL10 = "EDULEVEL";
    public static final String COL11 = "CORRECTANS";
    public static final String COL12 = "ANSWERED";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " TITLE TEXT, USERNAME TEXT, DESCRIPTION TEXT, CATEGORY TEXT, ANSWER1 TEXT, ANSWER2 TEXT, ANSWER3 TEXT, ANSWER4 TEXT, EDULEVEL TEXT, CORRECTANS TEXT, ANSWERED TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(SQLiteDatabase db, String title, String username, String description, String category, String answer1, String answer2, String answer3, String answer4, String eduLevel, String correctAns, String answered) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, title);
        contentValues.put(COL3, username);
        contentValues.put(COL4, description);
        contentValues.put(COL5, category);
        contentValues.put(COL6, answer1);
        contentValues.put(COL7, answer2);
        contentValues.put(COL8, answer3);
        contentValues.put(COL9, answer4);
        contentValues.put(COL10, eduLevel);
        contentValues.put(COL11, correctAns);
        contentValues.put(COL12, answered);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Cursor showData(SQLiteDatabase db ) {
        db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }


}
