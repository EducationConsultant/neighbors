package com.education.consultant.educon.service;

import com.education.consultant.educon.document.User;

import java.math.BigInteger;
import java.util.List;

public interface UserService {
    public User save(User user);
    public User update(User userToUpdate);
    public List<User> findAll();

    public User findByEmailAndPassword(String email, String password);
}
