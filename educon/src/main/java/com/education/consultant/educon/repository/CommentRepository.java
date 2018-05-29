package com.education.consultant.educon.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.education.consultant.educon.document.Comment;
import com.education.consultant.educon.document.Question;

@Repository
public interface CommentRepository extends MongoRepository<Comment, Long> {

    public List<Comment> findAllByOrderByIdDesc();
}
