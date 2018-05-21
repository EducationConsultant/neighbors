package com.education.consultant.educon.repository;

import com.education.consultant.educon.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface UserRepository extends MongoRepository<User, BigInteger> {

    User findByEmailAndPassword(String email, String password);

}
