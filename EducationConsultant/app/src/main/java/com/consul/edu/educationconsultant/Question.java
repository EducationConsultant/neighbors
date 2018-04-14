package com.consul.edu.educationconsultant;

/**
 * Created by Svetlana on 4/14/2018.
 */

public class Question {
    private String title, username, description, category;

    public Question() {
    }

    public Question(String title, String username, String description, String category) {
        this.title = title;
        this.username = username;
        this.description = description;
        this.category = category;
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
}
