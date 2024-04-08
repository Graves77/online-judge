package com.example.service.question.impl;

import com.example.mapper.question.QuestionMapper;
import com.example.mapper.question.RecordMapper;
import com.example.mapper.user.UserMapper;
import com.example.model.judge.SubmitRecord;
import com.example.model.judge.TestPack;
import com.example.model.judge.TestResult;
import com.example.model.judge.TestSample;
import com.example.model.question.Question;
import com.example.model.question.TestSamples;
import com.example.netty.NioWebSocketHandler;
import com.example.service.question.JudgeService;
import com.example.utils.ContainerUtils;
import com.example.utils.DateUtils;
import com.example.utils.SnowflakeIdWorker;
import com.example.utils.judge.Judger;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    public ContainerUtils containerUtils;

    @Autowired
    public QuestionMapper questionMapper;

    @Autowired
    public RecordMapper recordMapper;

    @Autowired
    public UserMapper userMapper;

    @Override
    public TestResult normalJudge(TestPack testPack) {
        return doJudge(testPack);
    }

    @Override
    public TestResult doJudge(TestPack testPack) {
        //创建时间
        Long time = System.currentTimeMillis();
        //拉取测试样例
        List<TestSample> testSamples = getTestSamples(testPack.getQid());
        //拉取得分点案例
        List<String> scoreSamples = getScoreSamples(testPack.getQid());
        testPack.setTestSampleList(testSamples);
        testPack.setTestScoreList(scoreSamples);
        //时间内空间
        testPack.setMemoryLimit(questionMapper.getQuestionMemoryLimit(testPack.getQid()));
        testPack.setTimeLimit(questionMapper.getQuestionTimeLimit(testPack.getQid()) * 1000);
        //对测试对象进行设置
        testPack.setSubmitTime(time);
        testPack.setSubmitTimeFormat(
                new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss").format(time)
        );
        //测试结果
        TestResult testResult = new TestResult();
        try {
            //新建判题机运行
            Judger judger = new Judger(testPack);
            testResult = judger.run();
        }finally {
            //删除判题机
            containerUtils.deleteContainer(testPack.getContainerId());
        }
        //保存记录
        return testResult;
    }

    /**
     * 得到问题 并 将测试用例格式化为数组
     * @param id 题目id
     * @return 测试用例集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TestSample> getTestSamples(long id) {
        TestSamples testSamples = questionMapper.getTestSample(id);
//        json解码
        JSONArray inputs = new JSONArray();
        JSONArray outputs = new JSONArray();
        if(testSamples != null){
            inputs = new JSONArray(testSamples.getSampleInput());
            outputs = new JSONArray(testSamples.getSampleOutput());
        }

        List<TestSample> testSample = new ArrayList<>();
//        遍历组装
        for (int i = 0; i < inputs.length(); i++) {
            testSample.add(new TestSample(
                    id,
                    inputs.get(i).toString(),
                    outputs.get(i).toString(),
                    "",
                    false
            ));
        }

        return testSample;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<String> getScoreSamples(long id) {
        String  scorePoint = questionMapper.getQuestionScorePoint(id);
//        json解码
        JSONArray score = new JSONArray();
        if(StringUtils.hasText(scorePoint)) {
            score = new JSONArray(scorePoint);
        }

        List<String> testSample = new ArrayList<>();
//        遍历组装
        for (int i = 0; i < score.length(); i++) {
            testSample.add(score.get(i).toString());
        }

        return testSample;
    }

    /**
     * 编译后相关设置
     * 如 用户解题数量增加
     * 题目通过梳理增加
     * 保存编译记录等
     * @param testResult
     * @param testPack
     * @return
     */
    @Override
    public boolean setting(TestResult testResult,TestPack testPack ){
        boolean flag = false;
//        报存编译记录
        flag = saveSubmitRecord(new SubmitRecord(
                SnowflakeIdWorker.snowFlow.nextId(),
                testResult.getUid(),
                testResult.getQid(),
                testPack.getQuestionName(),
                testResult.getTime(),
                testResult.getMemory(),
                testPack.getLanguage(),
                testPack.getCode(),
                testPack.getSubmitTimeFormat(),
                testResult.getTitle(),
                testResult.getMessage(),
                testResult.getTestSample() == null ? "{}" :
                        String.format("{\"input\":\"%s\",\"output\":\"%s\",\"userOutput\":\"%s\"}",
                                testResult.getTestSample().getInput(),
                                testResult.getTestSample().getOutput(),
                                testResult.getTestSample().getUserOutput()
                        ),
                testPack.getUserName()
        ));
//        更改用户解题数量
        if(testResult.isPass()){
//            确认这到底没有解锁过
            if(recordMapper.queryUserResolveQuestion(testPack.getUid(), testPack.getQid()) == 0){
//                增加结题数量
                String difficulty = questionMapper.queryQuestion(testPack.getQid()).getDifficulty();
                log.info(difficulty+"====="+testPack.getUid());
                userMapper.changeUserResolve(difficulty, testPack.getUid());
//                添加到已解决题目列表
                recordMapper.addUserResolve(testPack.getUid(), testPack.getQid());
            }

        }

        return flag;
    }

    @Override
    public boolean saveSubmitRecord(SubmitRecord submitRecord) {
        return recordMapper.addRecord(submitRecord);
    }

    /**
     * 必要设置，不参与事务回滚
     * @param testResult
     * @param testPack
     * @return
     */
    @Override
    public boolean mustSetting(TestResult testResult,TestPack testPack ){
        //更改题目总尝试\总通过
        boolean b = questionMapper.addTotalCount(
                testResult.isPass() ? 1 : 0, 1, testPack.getQid()
        );

        return b;
    }
}
