package com.example.controller.question;


import com.example.model.JsonResult;
import com.example.model.question.Question;
import com.example.service.question.QuestionService;
import com.example.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/addQuestion")
    public JsonResult addQuestion(@RequestBody Question question){
        boolean add = questionService.addQuestion(question);
        return new JsonResult(null,add? State.SUCCESS:State.FAILURE,add?"添加成功!":"添加失败!");
    }
}
