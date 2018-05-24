package com.education.consultant.educon.service;

import com.education.consultant.educon.document.Question;
import com.education.consultant.educon.wrappers.EduLevelWrapper;

import java.util.List;

public interface QuestionService {

    public Question save(Question question);
    public Question update(Long questionId, Question question);
    public Long deleteById(Long id);
    public List<Question> find();
    public List<Question> findByCategory(List<String> categories);
    public List<Question> findByEduLevel(List<String> eduLevels);
    public Question findOne(Long id);

}
