package com.education.consultant.educon.repository;

import com.education.consultant.educon.document.ResolveQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResolveQuestionRepository extends MongoRepository<ResolveQuestion, Long> {
    public List<ResolveQuestion> findAllByOrderByIdDesc();
}
