package com.example.mapper.question;


import com.example.model.question.Question;
import com.example.model.question.TestSamples;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 根据题目id得到测试样例信息
     * @param id 题目id
     * @return 测试样例对象
     */
    TestSamples getTestSample(long id);

    /**
     * 改变题目信息
     * @param question
     * @return
     */
    boolean changeQuestionInfo(Question question);

    /**
     * 改变题目测试数据
     * @param testSamples
     * @return
     */
    boolean changeQuestionTestSample(TestSamples testSamples);

    /**
     * 删除题目
     * @param qid
     * @return
     */
    boolean deleteQuestion(String qid);

    /**
     * 根据id查找题目
     * @param id
     * @return 题目对象
     */
    Question queryQuestion(long id);

    /**
     * 统计所有题目数量
     * @return
     */
    int countQuestion();

    /**
     * 得到用户做过的题目id
     * @param uid
     * @return
     */
    List<Long> getUserResolveQid(long uid);

    /**
     * 得到所有题目
     * @return
     */
    List<Question> queryQuestionList(long page);

    /**
     * 搜索题目
     * @param page
     * @param search
     * @return
     */
    List<Question> searchQuestion(int page,String search);

    /**
     * 得到题目的时间限制
     * @param id 题目id
     * @return 时间限制,单位秒
     */
    int getQuestionTimeLimit(long id);

    /**
     * 而得到题目的空间限制
     * @param id 题目id
     * @return 空间限制单位kb
     */
    int getQuestionMemoryLimit(long id);

    /**
     * 增加 总通过 和 总尝试
     * @param totalPass 通过
     * @param totalAttempt 尝试
     * @param id 题目id
     * @return
     */
    boolean addTotalCount(int totalPass,int totalAttempt,long id);

    String getQuestionScorePoint(long id);
}
