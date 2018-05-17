package com.consul.edu.educationconsultant.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.consul.edu.educationconsultant.adapters.ArchiveAdapter;
import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.model.Question;

import java.util.ArrayList;
import java.util.List;

public class ArchiveActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionBar;

    private List<Question> questionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArchiveAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        toolbar = (Toolbar) findViewById(R.id.toolbar_archive);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        // getSupportActionBar: using the toolbar from the Support Library
        actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        recyclerView = (RecyclerView) findViewById(R.id.archive_view);

        mAdapter = new ArchiveAdapter(this, questionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareQuestionData();

        // separator
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // set the adapter
        recyclerView.setAdapter(mAdapter);


    }

    private void prepareQuestionData() {
        Question question = new Question("Question A", "User 1", "This is my first question", "Mathematics", "a1", "a2" ,"a3", "a4", "Elementary School", "a1", "a1");
        questionList.add(question);

        question = new Question("Question B", "User 2", "This is my second question", "Sport", "a1", "a2" ,"a3", "a4", "Middle School","a1", "a2");
        questionList.add(question);

        question = new Question("Question C", "User 3", "This is my third question", "Mathematics",  "a1", "a2" ,"a3", "a4", "High School","a1", "a1");
        questionList.add(question);

        question = new Question("Question D", "User 4", "This is my fourth question", "English",  "a1", "a2" ,"a3", "a4", "Master's","a1", "a1");
        questionList.add(question);

        question = new Question("Question E", "User 5", "This is my fifth question", "Other",  "a1", "a2" ,"a3", "a4", "Doctor's","a1", "a1");
        questionList.add(question);

        mAdapter.notifyDataSetChanged();
    }
}
