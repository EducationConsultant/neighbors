package com.consul.edu.educationconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by user on 5/29/2018.
 */

public class Comment {

    @SerializedName("creator")
    @Expose
    private User creator;

    @SerializedName("question")
    @Expose
    private Question question;

    @SerializedName("text")
    @Expose
    private String text;

    public Comment() {}

    public Comment(User creator, Question question, String text) {
        this.creator = creator;
        this.question = question;
        this.text = text;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
