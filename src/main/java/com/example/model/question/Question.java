package com.example.model.question;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;


@Data
public class Question implements Serializable {
    /**
     * 题目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    /**
     * 题目名
     */
    private String questionName;
    /**
     * 输入格式说明
     */
    private String inputStyle;
    /**
     * 输出格式说明
     */
    private String outputStyle;
    /**
     * 例题 （json数组）
     */
    private String inputSample;
    /**
     * 例题解 （json数组）
     */
    private String outputSample;
    /**
     * 题目描述
     */
    private String description;
    /**
     * 总通过
     */
    private int totalPass;
    /**
     * 测试用力对象合集 （上传题目用）
     */
    private TestSamples testSamples;
    /**
     * 通过率
     */
    private double passRate;

    private int privateState = 0;
    /**
     * 用户是否通过
     */
    private int uid;
}
