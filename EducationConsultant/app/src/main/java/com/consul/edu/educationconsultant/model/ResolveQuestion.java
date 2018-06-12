package com.consul.edu.educationconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Danilo
 */

public class ResolveQuestion {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("idUsera")
    @Expose
    private Long idUsera;

    @SerializedName("idQuestion")
    @Expose
    private Long idQuestion;

    @SerializedName("answer")
    @Expose
    private String answer;

    @SerializedName("correctAns")
    @Expose
    private String correctAns;

    @SerializedName("questionText")
    @Expose
    private String questionText;

    public ResolveQuestion(){}

    public ResolveQuestion(Long idUsera, Long idQuestion, String answer, String correctAns){
        this.idUsera = idUsera;
        this.idQuestion = idQuestion;
        this.answer = answer;
        this.correctAns = correctAns;
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

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public String toString() {
        return "ResolveQuestion{" +
                "answer='" + answer + '\'' +
                ", idQuestion='" + idQuestion + '\'' +
                ", questionText='" + questionText+ '\'' +
                '}';

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
