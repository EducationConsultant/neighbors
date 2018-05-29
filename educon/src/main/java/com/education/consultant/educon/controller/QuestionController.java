package com.education.consultant.educon.controller;

import com.education.consultant.educon.document.Question;
import com.education.consultant.educon.service.QuestionService;
import com.education.consultant.educon.wrappers.FilterWrapper;
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
    public ResponseEntity<List<Question>> findAll() {
        List<Question> questions = questionService.findAll();
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }

    // find one
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Question> getQuestion(@PathVariable Long id) {
        Question question = questionService.findOne(id);
        return new ResponseEntity<Question>(question, HttpStatus.OK);
    }

    // find by filters
    @RequestMapping(value = "/filters/{radius}", method = RequestMethod.PUT)
    public ResponseEntity<List<Question>> findByFilters(@PathVariable int radius, @RequestBody FilterWrapper filters) {
        List<String> filtersStr = filters.getFilters();

        List<Question> questions = questionService.findByFilters(radius, filtersStr);
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }


    // insert
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Question> insertQuestion(@RequestBody Question question) {
        Question savedQuestion = questionService.save(question);
        return new ResponseEntity<Question>(savedQuestion, HttpStatus.CREATED);
    }

    // update question
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {

        Question savedQuestion = questionService.update(id, question);
        return new ResponseEntity<Question>(savedQuestion, HttpStatus.OK);
    }

    // delete question
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteQuestion(@PathVariable Long id) {
        questionService.deleteById(id);

        return new ResponseEntity<Long>(HttpStatus.OK);
    }

}
