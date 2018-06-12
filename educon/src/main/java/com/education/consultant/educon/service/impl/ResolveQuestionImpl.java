package com.education.consultant.educon.service.impl;

import com.education.consultant.educon.document.Comment;
import com.education.consultant.educon.document.ResolveQuestion;

import com.education.consultant.educon.document.User;
import com.education.consultant.educon.repository.ResolveQuestionRepository;
import com.education.consultant.educon.repository.UserRepository;
import com.education.consultant.educon.service.ResolveQuestionService;
import com.education.consultant.educon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResolveQuestionImpl implements ResolveQuestionService{

    @Autowired
    private ResolveQuestionRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public ResolveQuestion save(ResolveQuestion question) {
        List<ResolveQuestion> questions = repository.findAllByOrderByIdDesc();
        for(ResolveQuestion q : questions){
            Long nextId = q.getId() + 1;
            ResolveQuestion.setNextId(nextId);
            break;
        }
        question.setId(ResolveQuestion.getNextId());

        Long idUsera = question.getIdUsera();
        User user = userService.findOne(idUsera);
        user.getResolvedQuestions().add(question);
        userRepository.save(user);

        return repository.save(question);
    }

    @Override
    public List<ResolveQuestion> findAll() {
        return repository.findAll();
    }

    @Override
    public ResolveQuestion findOne(Long id) {
        Optional<ResolveQuestion> question = repository.findById(id);
        return question.get();
    }

    @Override
    public List<ResolveQuestion> findAllResolvedQuestions(Long userId) {
        List<ResolveQuestion> resolveQuestionsAll = repository.findAll();
        List<ResolveQuestion> resolveQuestionsOfUser = new ArrayList<>();

        for(ResolveQuestion rq : resolveQuestionsAll) {
            if(rq.getIdUsera().equals(userId)) {
                resolveQuestionsOfUser.add(rq);
            }
        }


        return resolveQuestionsOfUser;
    }
}


















