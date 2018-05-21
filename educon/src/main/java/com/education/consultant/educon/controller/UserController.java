package com.education.consultant.educon.controller;

import com.education.consultant.educon.document.User;
import com.education.consultant.educon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/educon/user")
public class UserController {

    @Autowired
    private UserService userService;

    // findAll
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    // insert user
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> insertUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);

        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    // find by email and password
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<User> findByEmailAndPassword(@RequestBody User user){
        User findedUser = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());

        return new ResponseEntity<User>(findedUser, HttpStatus.OK);
    }
}
