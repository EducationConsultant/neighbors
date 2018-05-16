package com.education.consultant.educon.service.impl;

import com.education.consultant.educon.document.Question;
import com.education.consultant.educon.repository.QuestionRepository;
import com.education.consultant.educon.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository repository;


    @Override
    public List<Question> find() {
        return repository.findAll();
    }

//    @Override
//    public Question findByTitle(String title) {
//        return repository.findByTitle(title);
//    }

    @Override
    public Question save(Question question) {
        return repository.save(question);
    }


}
