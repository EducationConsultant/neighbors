package com.education.consultant.educon.service;

import com.education.consultant.educon.document.Question;

import java.util.List;

public interface QuestionService {

    public Question save(Question question);
    public Question update(Long questionId, Question question);
    public Long deleteById(Long id);
    public List<Question> findAll();
    public List<Question> findByFilters(int radius, List<String> filters);
    public Question findOne(Long id);

}
