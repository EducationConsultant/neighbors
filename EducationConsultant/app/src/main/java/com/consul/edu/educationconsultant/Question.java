package com.consul.edu.educationconsultant;

/**
 * Created by Svetlana on 4/14/2018.
 */



// TODO 100: ADD COMMENTS
public class Question {
    private String title, username, description, category, answer1, answer2, answer3, answer4, eduLevel, correctAns, answered;


    public Question() {
    }

    public Question(String title, String username, String description, String category, String answer1, String answer2, String answer3, String answer4, String eduLevel, String correctAns, String answered) {
        this.title = title;
        this.username = username;
        this.description = description;
        this.category = category;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.eduLevel = eduLevel;
        this.correctAns = correctAns;
        this.answered = answered;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String genre) {
        this.username = username;
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

    public String getAnswered() { return answered; }

    public void setAnswered(String answered) { this.answered = answered; }
}
