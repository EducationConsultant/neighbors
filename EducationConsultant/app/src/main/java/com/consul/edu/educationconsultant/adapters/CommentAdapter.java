package com.consul.edu.educationconsultant.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.model.Comment;
import com.consul.edu.educationconsultant.model.Question;
import com.consul.edu.educationconsultant.model.User;

import java.util.ArrayList;

/**
 * Created by Svetlana on 5/29/2018.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    private static final String TAG = "CommentAdapter";

    private Context mContext;
    private int mResource;

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public CommentAdapter(Context context, int resource, ArrayList<Comment> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the comment information
        String text = getItem(position).getText();
        User creator = getItem(position).getCreator();
        Question question = getItem(position).getQuestion();

        // create the comment object with the information
        Comment comment = new Comment(creator, question, text);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tw1 = (TextView) convertView.findViewById(R.id.textView1);
        TextView tw2 = (TextView) convertView.findViewById(R.id.textView2);


        tw1.setText(text);
        tw2.setText(creator.getEmail());


        return convertView;




    }
}
