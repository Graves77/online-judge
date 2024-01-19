package com.example.mapper.question;


import com.example.model.question.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    /**
     * 插入一道题目
     * @param question 题目对象
     * @return 是否成功
     */
    boolean insertQuestion(Question question);

    /**
     * 插入一个题目的测试样例
     * @param question 题目对象
     * @return 是否成功
     */
    boolean insertTestSamples(Question question);
}
