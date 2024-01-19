package com.example.model.question;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestSamples {
    /**
     * 测试用例id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    /**
     * 题目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private long qid;
    /**
     * 题目名称
     */
    private String questionName;
    /**
     * 测试用例 （json数组）
     */
    private String sampleInput;
    /**
     * 测试用例 （json数组）
     */
    private String sampleOutput;
}
