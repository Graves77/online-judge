package com.example.service.question;


import com.example.model.question.Question;

public interface QuestionService {
    /**
     * 添加题目
     * @param question
     * @return
     */
    boolean addQuestion(Question question);
}
