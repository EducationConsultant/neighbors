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

    private String correctAns;

    private String questionText;

    private static Long nextId = 1L;

    public ResolveQuestion() {}

    public ResolveQuestion(Long idUsera, Long idQuestion, String answer, String correctAns, String questionText) {
        this.idUsera = idUsera;
        this.idQuestion = idQuestion;
        this.answer = answer;
        this.correctAns = correctAns;
        this.questionText = questionText;
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

    public String getCorrectAns() { return correctAns; }

    public void setCorrectAns(String correctAns) { this.correctAns = correctAns; }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
