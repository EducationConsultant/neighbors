package com.education.consultant.educon.repository;

import com.education.consultant.educon.document.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Question, Integer> {

    Question findByTitle(String titile);

}
