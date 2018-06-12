package com.education.consultant.educon.service;

import com.education.consultant.educon.document.ResolveQuestion;

import java.util.List;

public interface ResolveQuestionService {
    public ResolveQuestion save(ResolveQuestion question);
    public List<ResolveQuestion> findAll();

    public ResolveQuestion findOne(Long id);

    public List<ResolveQuestion> findAllResolvedQuestions(Long userId);

    public ResolveQuestion findQuestionByUser(Long userId, Long questionId);
}
