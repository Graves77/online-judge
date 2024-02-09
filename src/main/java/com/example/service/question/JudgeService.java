package com.example.service.question;

import com.example.model.judge.SubmitRecord;
import com.example.model.judge.TestPack;
import com.example.model.judge.TestResult;
import com.example.model.judge.TestSample;

import java.util.List;

public interface JudgeService {
    TestResult normalJudge(TestPack testPack);

    TestResult doJudge(TestPack testPack);

    List<TestSample> getTestSamples(long id);

    boolean setting(TestResult testResult,TestPack testPack );

    boolean saveSubmitRecord(SubmitRecord submitRecord);

    boolean mustSetting(TestResult testResult,TestPack testPack );
}
