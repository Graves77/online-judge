package com.example.service.question.impl;

import com.example.mapper.question.QuestionMapper;
import com.example.mapper.question.RecordMapper;
import com.example.model.question.Question;
import com.example.model.question.TestSamples;
import com.example.service.question.QuestionService;
import com.example.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private RecordMapper recordMapper;
    /**
     * 添加题目
     *
     * @param question
     * @return
     */
    @Override
    public boolean addQuestion(Question question) {
        log.info("-----------question对象数据：{}",question);
        boolean add = false;
        question.setId(SnowflakeIdWorker.snowFlow.nextId());
        add = questionMapper.insertQuestion(question);
        add = questionMapper.insertTestSamples(question);
        return add;
    }

    /**
     * @param qid
     * @return
     */
    @Override
    public TestSamples getQuestionTestSample(String qid) {
        return questionMapper.getTestSample(Long.parseLong(qid));
    }

    /**
     * 更改题目信息
     *
     * @param question
     * @return
     */
    @Override
    @Transactional
    public boolean changeQuestionInfo(Question question) {
        boolean isChange = false;
        isChange = questionMapper.changeQuestionInfo(question);
        isChange = questionMapper.changeQuestionTestSample(question.getTestSamples());
        return isChange;
    }

    /**
     * 删除题目
     *
     * @param qid
     * @return
     */
    @Override
    public boolean deleteQuestion(String qid) {
        return questionMapper.deleteQuestion(qid);
    }

    @Override
    public Question queryQuestion(long id) {
        return questionMapper.queryQuestion(id);
    }

    @Override
    public int countQuestion() {
        return questionMapper.countQuestion();
    }


    @Override
    public List<Question> queryQuestionList(int page,long uid) {
        page *= 15;
        List<Long> userResolveQid = questionMapper.getUserResolveQid(uid);
        List<Question> questions = questionMapper.queryQuestionList(page);
        System.out.println(userResolveQid);
        for (Question question : questions) {
            for (Long integer : userResolveQid) {
                if(question.getId() == integer){
                    question.setUid(1);
                }
            }
        }
        return computedPassRate(questions);
    }



    @Override
    public List<Question> querySearchQuestionList(int page, String search) {
        return computedPassRate(questionMapper.searchQuestion(page, search));
    }

    public List<Question> computedPassRate(List<Question> questions){
        for (Question question : questions) {
            if(question.getTotalAttempt() != 0){
                DecimalFormat df = new DecimalFormat("#.00");
                question.setPassRate(
                        Double.parseDouble(df.format(((question.getTotalPass() * 1.0) / (question.getTotalAttempt() * 1.0))))
                );

            }
        }
        return questions;
    }

    /**
     * 得到用户解决过的题目
     *
     * @param uid 用户id
     * @return 已题目列表
     */
    @Override
    public List<Long> getUserResolveQuestionId(long uid) {
        return recordMapper.getUserResolveRecord(uid);
    }


}
