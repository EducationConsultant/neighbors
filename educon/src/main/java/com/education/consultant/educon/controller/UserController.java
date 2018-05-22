package com.education.consultant.educon.controller;

import com.education.consultant.educon.document.User;
import com.education.consultant.educon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // find by email and password
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<User> findByEmailAndPassword(@RequestBody User user){
        User resultUser = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());

        return new ResponseEntity<User>(resultUser, HttpStatus.OK);
    }

    // find by email
    @RequestMapping(value = "/email",method = RequestMethod.PUT)
    public ResponseEntity<User> findByEmail(@RequestBody User user){
        User resultUser = userService.findByEmail(user.getEmail());

        return new ResponseEntity<User>(resultUser, HttpStatus.OK);
    }

    // insert user
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> insertUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);

        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    // update user
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private ResponseEntity<User> updateUser(@PathVariable Long id,
                                            @Valid @RequestBody User user) {

        User savedUser = userService.update(id, user);
        return new ResponseEntity<User>(savedUser, HttpStatus.OK);
    }

    // update password
    @RequestMapping(value = "/{id}/password", method = RequestMethod.PUT)
    private ResponseEntity<User> updateUserPassword(@PathVariable Long id, @RequestBody String newPassword) {

        User savedUser = userService.updateUserPassword(id, newPassword);
        return new ResponseEntity<User>(savedUser, HttpStatus.OK);
    }

}
