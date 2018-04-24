package com.education.consultant.educon.repository;

import com.education.consultant.educon.document.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, Integer> {


}
