package com.example.service.question;


import com.example.model.question.Question;
import com.example.model.question.TestSamples;

import java.util.List;

public interface QuestionService {
    /**
     * 添加题目
     * @param question
     * @return
     */
    boolean addQuestion(Question question);

    TestSamples getQuestionTestSample(String qid);

    /**
     * 更改题目信息
     * @param question
     * @return
     */
    boolean changeQuestionInfo(Question question);

    /**
     * 删除题目
     * @param qid
     * @return
     */
    boolean deleteQuestion(String qid);

    /**
     * 根据id拿题目
     * @param id 题目id
     * @return 题目对象
     */
    Question queryQuestion(long id);

    int countQuestion();

    /**
     * 得到用户解决过的题目
     * @param uid 用户id
     * @return 已题目列表
     */
    //List<Long> getUserResolveQuestionId(long uid);

    /**
     * 得到所有题目
     * @return 题目对象集合
     */
    List<Question> queryQuestionList(int page,long uid);

    /**
     *
     * @param page
     * @param search
     * @return
     */
    List<Question> querySearchQuestionList(int page,String search);


}
