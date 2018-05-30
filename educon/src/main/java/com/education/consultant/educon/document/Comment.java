package com.education.consultant.educon.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Document
public class Comment {

   @Id
   private Long id;
  
   private String text;
  
   @DBRef
   private User creator;
   
   @DBRef
   @JsonBackReference
   private Question question;
  
   private static Long nextId = 1L;
  
   public Comment() {}

	public Comment(String text, User creator, Question question) {
		this.text = text;
		this.creator = creator;
		this.question = question;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
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
		Comment.nextId = nextId;
	}
	
	   
		
		  
		
	  
	  
}
