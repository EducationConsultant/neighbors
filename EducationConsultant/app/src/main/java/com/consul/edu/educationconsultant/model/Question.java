package com.consul.edu.educationconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Svetlana on 4/14/2018.
 */

// TODO : ADD COMMENTS
public class Question {

    @SerializedName("owner")
    @Expose
    private User owner;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("answer1")
    @Expose
    private String answer1;

    @SerializedName("answer2")
    @Expose
    private String answer2;

    @SerializedName("answer3")
    @Expose
    private String answer3;

    @SerializedName("answer4")
    @Expose
    private String answer4;

    @SerializedName("eduLevel")
    @Expose
    private String eduLevel;

    @SerializedName("correctAns")
    @Expose
    private String correctAns;

    @SerializedName("answered")
    @Expose
    private String answered;


    @SerializedName("commentList")
    @Expose
    private List<Comment>  commentList;

    public Question() {
    }

    // create question for server
    public Question(User owner,
                    String description, String category,
                    String answer1, String answer2,
                    String answer3, String answer4,
                    String eduLevel) {
        this.owner = owner;
        this.description = description;
        this.category = category;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.eduLevel = eduLevel;
    }

    // create question for test
    public Question(User owner,
                    String description, String category,
                    String answer1, String answer2,
                    String answer3, String answer4,
                    String eduLevel, String answered, String correctAns) {
        this.owner = owner;
        this.description = description;
        this.category = category;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.eduLevel = eduLevel;
        this.answered = answered;
        this.correctAns = correctAns;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getEduLevel() { return eduLevel; }

    public void setEduLevel(String eduLevel) { this.eduLevel = eduLevel; }

    public String getCorrectAns() { return correctAns; }

    public void setCorrectAns(String correctAns) { this.correctAns = correctAns; }

    public String getAnswered() {
        return answered;
    }

    public void setAnswered(String answered) { this.answered = answered; }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment>  commentList) {
        this.commentList = commentList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Question{" +
                "description='" + description + '\'' +
                ", owner.email='" + owner.getEmail() + '\'' +
                '}';

    }
}
