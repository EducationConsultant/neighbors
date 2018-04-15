package com.consul.edu.educationconsultant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class AddQuestionActivity extends AppCompatActivity {

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
            String eduLevelStr = ""; //eduLevel.getText().toString().trim();
            String categoryStr = "";//category.getText().toString().trim();

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

            /*
            * TODO
            * Here should go sending the data to server and storing it
            */
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
