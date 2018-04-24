package com.education.consultant.educon.controller;

import com.education.consultant.educon.document.Question;
import com.education.consultant.educon.repository.QuestionRepository;
import com.education.consultant.educon.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/educon/question")
public class QuestionController {


    @Autowired
    private QuestionService questionService;


    // find all
    // localhost:8095/educon/question
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Question>> getQuestions() {
        List<Question> questions = questionService.find();
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }

    // find by title
    // localhost:8095/educon/question/title?title=naslov
    @RequestMapping(value = "/title", method = RequestMethod.GET)
    public ResponseEntity<Question> findByTitle(@RequestParam(value = "title") String title) {
        Question questions = questionService.findByTitle(title);
        return new ResponseEntity<Question>(questions, HttpStatus.OK);
    }

    // insert
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Question> insertQuestion(@RequestBody Question question) {
        Question savedQuestion = questionService.save(question);
        return new ResponseEntity<Question>(savedQuestion, HttpStatus.CREATED);
    }


}
