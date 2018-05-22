package com.education.consultant.educon.service;

import com.education.consultant.educon.document.User;

import java.util.List;

public interface UserService {
    public User save(User user);
    public User update(Long userId, User user);
    public User updateUserPassword(Long userId, String newPassword);
    public List<User> findAll();
    public User findByEmailAndPassword(String email, String password);
    public User findByEmail(String email);
    public User findOne(Long id);
}
