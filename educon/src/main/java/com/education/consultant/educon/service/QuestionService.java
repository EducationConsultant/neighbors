package com.education.consultant.educon.service;

import com.education.consultant.educon.document.Question;
import org.springframework.data.annotation.Id;

import java.util.List;

public interface QuestionService {

    public List<Question> find();
    public Question findByTitle(String title);
    public Question save(Question question);
}
