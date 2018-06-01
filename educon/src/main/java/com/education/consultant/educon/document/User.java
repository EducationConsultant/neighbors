package com.education.consultant.educon.document;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

// TODO: Add answeredQuestions
@Document
public class User {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String password;


   // @JsonManagedReference
    private List<ResolveQuestion> resolvedQuestions;

    private static Long nextId = 1L;

    public User(){

    }

    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Long getNextId() {
        return nextId;
    }

    public static void setNextId(Long nextId) {
        User.nextId = nextId;
    }

    public List<ResolveQuestion> getResolvedQuestions() {
        return resolvedQuestions;
    }

    public void setResolvedQuestions(List<ResolveQuestion> resolvedQuestions) {
        this.resolvedQuestions = resolvedQuestions;
    }

    @Override
    public String toString() {
        return "User [First name: " + firstName + ", Last name: " + lastName +", Email: " + email + "]";
    }
}
