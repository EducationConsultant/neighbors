package com.education.consultant.educon.controller;

import com.education.consultant.educon.document.ResolveQuestion;
import com.education.consultant.educon.service.ResolveQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/educon/resolvequestion")
public class ResolveQuestionController {

    @Autowired
    private ResolveQuestionService resolveQuestionService;

    // find all
    // localhost:8095/educon/resolvequestion
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ResolveQuestion>> findAll() {
        List<ResolveQuestion> questions = resolveQuestionService.findAll();
        return new ResponseEntity<List<ResolveQuestion>>(questions, HttpStatus.OK);
    }

    // find one
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<ResolveQuestion> getResolveQuestion(@PathVariable Long id) {
        ResolveQuestion question = resolveQuestionService.findOne(id);
        return new ResponseEntity<ResolveQuestion>(question, HttpStatus.OK);
    }

    // insert
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResolveQuestion> insertResolveQuestion(@RequestBody ResolveQuestion question) {
        ResolveQuestion savedQuestion = resolveQuestionService.save(question);
        return new ResponseEntity<ResolveQuestion>(savedQuestion, HttpStatus.CREATED);
    }

    // find all resolved questions of one user
    // localhost:8095/educon/resolvequestion/archive/{userId}
    @RequestMapping(value = "/archive/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<ResolveQuestion>> findAllRQ(@PathVariable Long userId) {
        List<ResolveQuestion> resolvedQuestions = resolveQuestionService.findAllResolvedQuestions(userId);
        return new ResponseEntity<List<ResolveQuestion>>(resolvedQuestions, HttpStatus.OK);
    }



}
