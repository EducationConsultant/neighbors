package com.education.consultant.educon.document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ResolveQuestion {

    @Id
    private Long id;

    private Long idUsera;

    private Long idQuestion;

    private String answer;

    private static Long nextId = 1L;

    public ResolveQuestion() {}

    public ResolveQuestion(Long idUsera, Long idQuestion, String answer) {
        this.idUsera = idUsera;
        this.idQuestion = idQuestion;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsera() {
        return idUsera;
    }

    public void setIdUsera(Long idUsera) {
        this.idUsera = idUsera;
    }

    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static Long getNextId() {
        return nextId;
    }

    public static void setNextId(Long nextId) {
        ResolveQuestion.nextId = nextId;
    }
}
