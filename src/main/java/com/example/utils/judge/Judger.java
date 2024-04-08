package com.example.utils.judge;

import com.example.model.judge.TestPack;
import com.example.model.judge.TestResult;
import com.example.netty.NioWebSocketHandler;
import lombok.extern.log4j.Log4j2;

/**
 * @author 凯威
 * 判题辅助,集成判题机核心功能
 */
@Log4j2
public class Judger extends JudgeCore{


    public Judger(TestPack testPack) {
        System.out.println(testPack);
        this.testPack = testPack;
    }

    /**
     * 完整的判题机运行过程
     */
    public TestResult run(){

        //  初始化
        this.init();
        //  编译
        log.info("2.编译中...编译完毕");
        String compile = this.compile();
        System.out.println("编译 :" + compile);
        // 编译结果需要为空或null才是编译成功，可以继续执行代码
        if("".equals(compile) || compile == null || " ".equals(compile)){
            log.info("编译成功");
            log.info("3.运行中");
            String execute = this.execute();
            System.out.println("运行 :" + execute);
            log.info("...运行完毕");
           testResult.setTestScore(checkAnswer());
           testResult.setGetScore(checkScorePoint());
        }else{
            log.info("编译错误");
            testResult.setPass(false);
            testResult.setTitle("Compile Error");
            testResult.setMessage(compile);
        }

        if(testResult.isPass()){
            log.info("4.返回结果...运行完毕");
        }
        return testResult;

    }
}
