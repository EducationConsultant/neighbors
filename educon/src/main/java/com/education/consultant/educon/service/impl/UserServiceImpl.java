package com.education.consultant.educon.service.impl;

import com.education.consultant.educon.document.ResolveQuestion;
import com.education.consultant.educon.document.User;
import com.education.consultant.educon.repository.UserRepository;
import com.education.consultant.educon.service.EmailService;
import com.education.consultant.educon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public User save(User user) {
        User userInDataBase = userRepository.findByEmail(user.getEmail());

        if(userInDataBase == null) {
            List<User> users = userRepository.findAllByOrderByIdDesc();
            for (User u : users) {
                Long nextId = u.getId() + 1;
                User.setNextId(nextId);
                break;
            }

            List<ResolveQuestion> resolvedQuestions = new ArrayList<>();
            user.setResolvedQuestions(resolvedQuestions);

            user.setId(User.getNextId());

            if(user.getPassword().equals("")){
                String password = generatePassword();
                user.setPassword(password);

                emailService.sendEmail(user, "Welcome");
            }

            return userRepository.save(user);
        }else{
            return userInDataBase;
        }
    }

    @Override
    public User update(Long userId, User user) {
        User userToUpdate = findOne(userId);

        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());

        User saved = userRepository.save(userToUpdate);
        return saved;
    }

    @Override
    public User updateUserPassword(Long userId, String newPassword) {
        User userToUpdate = findOne(userId);

        userToUpdate.setPassword(newPassword);

        User saved = userRepository.save(userToUpdate);
        return saved;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findOne(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    private String generatePassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
