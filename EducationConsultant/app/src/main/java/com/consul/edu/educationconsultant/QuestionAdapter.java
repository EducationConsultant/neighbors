package com.consul.edu.educationconsultant;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Svetlana on 4/14/2018.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private List<Question> questionList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, username;
        RelativeLayout parentLayout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            username = (TextView) view.findViewById(R.id.username);
            parentLayout = view.findViewById(R.id.parent_layout);
        }
    }


    public QuestionAdapter(Context context, List<Question> questionList) {
        this.questionList = questionList;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Question question = questionList.get(position);
        holder.title.setText(question.getTitle());
        holder.username.setText(question.getUsername());


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("title", questionList.get(position).getTitle());
                intent.putExtra("username", questionList.get(position).getUsername());
                intent.putExtra("description", questionList.get(position).getDescription());
                intent.putExtra("category", questionList.get(position).getCategory());
                intent.putExtra("answer1", questionList.get(position).getAnswer1());
                intent.putExtra("answer2", questionList.get(position).getAnswer2());
                intent.putExtra("answer3", questionList.get(position).getAnswer3());
                intent.putExtra("answer4", questionList.get(position).getAnswer4());
                intent.putExtra("eduLevel", questionList.get(position).getEduLevel());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
