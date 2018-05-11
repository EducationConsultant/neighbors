package com.consul.edu.educationconsultant.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.database.DatabaseHelper;
import com.consul.edu.educationconsultant.model.Question;

public class AddQuestionActivity extends AppCompatActivity {

    private DatabaseHelper questionDB;
    private SQLiteDatabase db;
    private Cursor cursor;

    private Button btnAdd;
    private EditText question;
    private EditText ansOne;
    private EditText ansTwo;
    private EditText ansThree;
    private EditText ansFour;
    //private EditText eduLevel;
    //private EditText category;
    private Spinner eduLevel;
    private Spinner category;


    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        progressBar = findViewById(R.id.progressBar);

        /*
        * config spinner for education levels
        */
        eduLevel = (Spinner) findViewById(R.id.edu_level);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> eduAdapter = ArrayAdapter.createFromResource(this,
                R.array.edu_levels_arr, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        eduLevel.setAdapter(eduAdapter);

        /*
        * config spinner for categories
         */
        category = (Spinner) findViewById(R.id.category);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_arr, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category.setAdapter(categoryAdapter);

        questionDB = new DatabaseHelper(this);


    }

    /**
     * TODO
     * Spinner choice made listener
     */


    /**
     * When button add clicked
     */
    public void onClickAddQuestionBtn(View view){
        question = findViewById(R.id.question);
        ansOne = findViewById(R.id.ans_one);
        ansTwo = findViewById(R.id.ans_two);
        ansThree = findViewById(R.id.ans_three);
        ansFour = findViewById(R.id.ans_four);
        eduLevel = findViewById(R.id.edu_level);
        category = findViewById(R.id.category);

        btnAdd = findViewById(R.id.btn_add);

        if(btnAdd != null){
            Question newQuestion = new Question();

            String questionStr = question.getText().toString().trim();
            String ansOneStr = ansOne.getText().toString().trim();
            String ansTwoStr = ansTwo.getText().toString().trim();
            String ansThreeStr = ansThree.getText().toString().trim();
            String ansFourStr = ansFour.getText().toString().trim();
            String eduLevelStr = "foo"; //eduLevel.getText().toString().trim();
            String categoryStr = "bar";//category.getText().toString().trim();

            /*
             * check if all fields are field out
             */
            if (TextUtils.isEmpty(questionStr)){
                return;
            }

            if (TextUtils.isEmpty(ansOneStr)){
                return;
            }

            if (TextUtils.isEmpty(ansTwoStr)){
                return;
            }

            if (TextUtils.isEmpty(ansThreeStr)){
                return;
            }

            if (TextUtils.isEmpty(ansFourStr)){
                return;
            }

            if (TextUtils.isEmpty(eduLevelStr)){
                return;
            }

            if (TextUtils.isEmpty(categoryStr)){
                return;
            }

            newQuestion.setDescription(questionStr);
            newQuestion.setAnswer1(ansOneStr);
            newQuestion.setAnswer2(ansTwoStr);
            newQuestion.setAnswer3(ansThreeStr);
            newQuestion.setAnswer4(ansFourStr);
            newQuestion.setEduLevel(eduLevelStr);
            newQuestion.setCategory(categoryStr);
            newQuestion.setCorrectAns(ansOneStr);
            newQuestion.setAnswered("");

            /*
            * TODO
            * Here should go sending the data to server and storing it
            */


            // insert into database
            try {
                db = questionDB.getWritableDatabase();
                boolean insertData = questionDB.addData(db, "Title", "user", questionStr, categoryStr,ansOneStr,ansTwoStr,ansThreeStr,ansFourStr,eduLevelStr,ansOneStr,"");

//                if (insertData == true) {
//                    Toast.makeText(AddQuestionActivity.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(AddQuestionActivity.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
//                }
        }
            catch(SQLiteException e) {
                Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
            }

            // check data with cursor -- for test
            cursor = db.query("question_table",
                    new String[]{"DESCRIPTION"},
                    "DESCRIPTION = ?",
                    new String[] {questionStr},
                    null,null,null);

            if(cursor.moveToFirst()){
                do{
                    String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                    Toast toast = Toast.makeText(this,description,Toast.LENGTH_SHORT);
                    toast.show();
                }while (cursor.moveToNext());
            }



            // go back to home
            Intent questionList = new Intent(AddQuestionActivity.this, NavigationDrawerActivity.class);
            startActivity(questionList);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
