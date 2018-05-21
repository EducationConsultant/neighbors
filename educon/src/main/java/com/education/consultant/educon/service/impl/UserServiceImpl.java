package com.education.consultant.educon.service.impl;

import com.education.consultant.educon.document.User;
import com.education.consultant.educon.repository.UserRepository;
import com.education.consultant.educon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        List<User> users = userRepository.findAllByOrderByIdDesc();
        for (User u : users) {
            Long nextId = u.getId()+1;
            User.setNextId(nextId);
            break;
        }
        user.setId(User.getNextId());
        return userRepository.save(user);
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
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public User findOne(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }
}
