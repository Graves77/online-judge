package com.example.service.question.impl;

import com.example.mapper.question.QuestionMapper;
import com.example.model.question.Question;
import com.example.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 添加题目
     *
     * @param question
     * @return
     */
    @Override
    public boolean addQuestion(Question question) {
        boolean add = false;
        add = questionMapper.insertQuestion(question);
        add = questionMapper.insertTestSamples(question);
        return add;
    }
}
