package com.example.controller.question;


import com.example.model.JsonResult;
import com.example.model.judge.TestPack;
import com.example.model.judge.TestResult;
import com.example.service.question.JudgeService;
import com.example.utils.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class JudgeController {


    @Autowired
    public JudgeService judgeService;


    @PostMapping("/judge")
    public JsonResult judgeAll(@RequestBody TestPack testPack) {

        /**
         * 判题
         */
        TestResult testResult = null;
        boolean setting = false;
        if (testPack.getType().equals(100)) {
//            普通提交走这里
            log.info("普通提交");
            testResult = judgeService.normalJudge(testPack);//基本的代码提交以及判断编译是否成功并且把测试案例导入测试
            setting = judgeService.setting(testResult, testPack);//保存个人的提交记录
            setting = judgeService.mustSetting(testResult, testPack);//更改题目的通过数量
        } else {
            return new JsonResult(null, State.FAILURE,"提交失败,没有这个提交选项");
        }
        return new JsonResult(testResult,!setting ? State.FAILURE : State.SUCCESS, !setting ? "提交失败,请联系管理员!":"提交成功!");
    }

}