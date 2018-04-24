package com.education.consultant.educon.controller;

import com.education.consultant.educon.document.Question;
import com.education.consultant.educon.repository.QuestionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educon/question")
public class QuestionController {


    private QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    // http://localhost:8095/educon/question/all
    @GetMapping("/all")
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

}
