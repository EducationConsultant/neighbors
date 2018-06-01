package com.education.consultant.educon.service.impl;

import com.education.consultant.educon.document.Comment;
import com.education.consultant.educon.document.Question;

import com.education.consultant.educon.document.User;
import com.education.consultant.educon.repository.CommentRepository;


import com.education.consultant.educon.repository.QuestionRepository;
import com.education.consultant.educon.repository.UserRepository;
import com.education.consultant.educon.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Question> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Question> findByFilters(int radius, List<String> filters) {
        Set<Question> resultQuestions = new HashSet<>();
        List<Question> allQuestions = repository.findAll();

        for(Question question:allQuestions){
            for(String filter : filters){
                if(question.getCategory().equals(filter) || question.getEduLevel().equals(filter)) {
                    resultQuestions.add(question);
                }
            }
        }

        // TODO: add filter by location

        List<Question> questionsSorted = new ArrayList<>();
        for(Question q : resultQuestions) {
            questionsSorted.add(q);
        }

        Collections.sort(questionsSorted, new Comparator<Question>() {
            @Override
            public int compare(Question question1, Question question2) {
                return question1.getId().compareTo(
                        question2.getId());
            }
        });

        return questionsSorted;
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
        
        List<Comment> comments = new ArrayList<>();
        question.setComments(comments);
  

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

	@Override
	public Comment saveComment(Long questionId, Comment comment) {
		Question q = findOne(questionId);
		comment.setQuestion(q);
		
	    List<Comment> comments = commentRepository.findAllByOrderByIdDesc();
        for(Comment c : comments){
            Long nextId = c.getId() + 1;
            Comment.setNextId(nextId);
            break;
        }
        comment.setId(Comment.getNextId());
        
        q.getComments().add(comment);
        repository.save(q);
		
		return commentRepository.save(comment);
	}

	@Override
	public List<Comment> findAllComments(Long questionId) {
		List<Comment> commentsAll = commentRepository.findAll();
		List<Comment> commentsOfQuestion = new ArrayList<Comment>();
		
		for (Comment c : commentsAll) {
			if(c.getQuestion().getId().equals(questionId)) {
				commentsOfQuestion.add(c);
			}
		}
		return commentsOfQuestion;
	}

	@Override
	public List<Comment> findAllComm() {
		return commentRepository.findAll();
	}




}
