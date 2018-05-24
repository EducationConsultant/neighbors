package com.education.consultant.educon.service.impl;

import com.education.consultant.educon.document.Question;
import com.education.consultant.educon.document.User;
import com.education.consultant.educon.repository.QuestionRepository;
import com.education.consultant.educon.repository.UserRepository;
import com.education.consultant.educon.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;


    @Override
    public List<Question> find() {
        return repository.findAll();
    }

    @Override
    public List<Question> findByCategory(List<String> categories) {
        return null;
    }

    @Override
    public List<Question> findByEduLevel(List<String> eduLevels) {
        List<Question> resultQuestions = new ArrayList<Question>();
        for(String eduLevel : eduLevels){
            resultQuestions.add(repository.findByEduLevel(eduLevel));
        }
        return resultQuestions;
    }

    @Override
    public Question findOne(Long id) {
        Optional<Question> question = repository.findById(id);
        return question.get();
    }


    @Override
    public Question save(Question question) {
        List<Question> questions = repository.findAllByOrderByIdDesc();
        for(Question q : questions){
            Long nextId = q.getId() + 1;
            Question.setNextId(nextId);
            break;
        }
        question.setId(Question.getNextId());
        question.setCorrectAns(question.getAnswer1());
        question.setAnswered("");

        return repository.save(question);
    }

    @Override
    public Question update(Long questionId, Question question) {
        Question questionToUpdate = findOne(questionId);

        questionToUpdate.setDescription(question.getDescription());
        questionToUpdate.setCategory(question.getCategory());
        questionToUpdate.setAnswer1(question.getAnswer1());
        questionToUpdate.setAnswer2(question.getAnswer2());
        questionToUpdate.setAnswer3(question.getAnswer3());
        questionToUpdate.setAnswer4(question.getAnswer4());
        questionToUpdate.setEduLevel(question.getEduLevel());
        questionToUpdate.setCorrectAns(question.getAnswer1());

        Question saved = repository.save(questionToUpdate);

        return saved;
    }

    @Override
    public Long deleteById(Long id) {
        Question questionInDataBase = findOne(id);
        repository.delete(questionInDataBase);

        return id;
    }


}
