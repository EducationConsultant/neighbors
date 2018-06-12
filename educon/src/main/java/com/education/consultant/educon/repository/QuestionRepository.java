package com.education.consultant.educon.repository;

import com.education.consultant.educon.document.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, Long> {

    public List<Question> findAllByOrderByIdDesc();

    public Question findByDescription(String description);
}
