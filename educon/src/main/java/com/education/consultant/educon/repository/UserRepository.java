package com.education.consultant.educon.repository;

import com.education.consultant.educon.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Long> {

    public User findByEmailAndPassword(String email, String password);

    public User findByEmail(String email);

    public List<User> findAllByOrderByIdDesc();

}
