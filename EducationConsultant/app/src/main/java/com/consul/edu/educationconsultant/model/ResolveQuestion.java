package com.consul.edu.educationconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Danilo
 */

public class ResolveQuestion {

    @SerializedName("idUsera")
    @Expose
    private Long idUsera;

    @SerializedName("idQuestion")
    @Expose
    private Long idQuestion;

    @SerializedName("answer")
    @Expose
    private String answer;

    public ResolveQuestion(){}

    public ResolveQuestion(Long idUsera, Long idQuestion, String answer){
        this.idUsera = idUsera;
        this.idQuestion = idQuestion;
        this.answer = answer;
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

    @Override
    public String toString() {
        return "ResolveQuestion{" +
                "answer='" + answer + '\'' +
                ", idQuestion='" + idQuestion + '\'' +
                '}';

    }
}
