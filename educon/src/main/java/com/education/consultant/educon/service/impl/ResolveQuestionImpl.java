package com.education.consultant.educon.service.impl;

import com.education.consultant.educon.document.Comment;
import com.education.consultant.educon.document.Question;
import com.education.consultant.educon.document.ResolveQuestion;

import com.education.consultant.educon.document.User;
import com.education.consultant.educon.repository.QuestionRepository;
import com.education.consultant.educon.repository.ResolveQuestionRepository;
import com.education.consultant.educon.repository.UserRepository;
import com.education.consultant.educon.service.QuestionService;
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

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public ResolveQuestion save(ResolveQuestion question) {
        ResolveQuestion newResolvedQ = new ResolveQuestion();

        List<ResolveQuestion> questions = repository.findAllByOrderByIdDesc();
        for(ResolveQuestion q : questions){
            Long nextId = q.getId() + 1;
            ResolveQuestion.setNextId(nextId);
            break;
        }
        newResolvedQ.setId(ResolveQuestion.getNextId());
        newResolvedQ.setIdUsera(question.getIdUsera());
        newResolvedQ.setAnswer(question.getAnswer());
        newResolvedQ.setCorrectAns(question.getCorrectAns());
        newResolvedQ.setQuestionText(question.getQuestionText());

        Question questionM = questionRepository.findByDescription(question.getQuestionText());
        newResolvedQ.setIdQuestion(questionM.getId());

        ResolveQuestion saved = repository.save(newResolvedQ);

        Long idUsera = question.getIdUsera();
        User user = userService.findOne(idUsera);
        List<ResolveQuestion> resolvedQuestions = user.getResolvedQuestions();
        resolvedQuestions.add(question);
        user.setResolvedQuestions(resolvedQuestions);
        userRepository.save(user);

        return saved;
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

    @Override
    public ResolveQuestion findQuestionByUser(Long userId, Long questionId) {
        User user = userService.findOne(userId);
        List<ResolveQuestion> resolveQuestions = user.getResolvedQuestions();
        ResolveQuestion result = new ResolveQuestion(userId,0L,"","","");

        for(ResolveQuestion resolveQ:resolveQuestions){
            System.err.print("Prosledjeni question: " + questionId+ "\n");
            System.err.print(resolveQ.getQuestionText()+ "\n");
            System.err.print(resolveQ.getIdUsera()+ "\n");
            System.err.print(resolveQ.getIdQuestion()+"\n");
            if(resolveQ.getIdQuestion() == questionId){
                System.err.print("nasao"+"\n");
                result = resolveQ;
                break;
            }
        }

        System.err.print(result.getQuestionText()+ "\n");
        System.err.print(result.getIdUsera()+ "\n");
        System.err.print(result.getIdQuestion()+"\n");
        return result;
    }
}


















