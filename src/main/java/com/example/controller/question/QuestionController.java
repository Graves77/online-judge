package com.example.controller.question;


import com.example.model.JsonResult;
import com.example.model.question.Question;
import com.example.service.question.QuestionService;
import com.example.utils.State;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/addQuestion")
    public JsonResult addQuestion(@RequestBody Question question){
        boolean add = questionService.addQuestion(question);
        return new JsonResult(null,add? State.SUCCESS:State.FAILURE,add?"添加成功!":"添加失败!");
    }

    @GetMapping("/getQuestionTestSample/{qid}")
    public JsonResult getQuestionTestSample(@PathVariable String qid){
        return new JsonResult(questionService.getQuestionTestSample(qid),State.SUCCESS,"获取成功");
    }

    @PostMapping(value = "/changeQuestionInfo")
    public JsonResult changeQuestionInfo(@RequestBody Question question){
        boolean change = questionService.changeQuestionInfo(question);
        return new JsonResult(null,change ? State.SUCCESS : State.FAILURE,change ? "更改成功":"更改失败");
    }

    @DeleteMapping("/deleteQuestion")
    public JsonResult deleteQuestion(@Param("qid") String qid){
        boolean delete = questionService.deleteQuestion(qid);
        return new JsonResult(null,delete ? State.SUCCESS : State.FAILURE,delete ? "删除成功":"删除失败");
    }

    @GetMapping(value = "/getQuestion/{id}")
    public JsonResult getQuestion(@PathVariable long id){
        return new JsonResult(questionService.queryQuestion(id),State.SUCCESS,"获取成功");
    }

    @GetMapping(value = "/countQuestion")
    public JsonResult countQuestion(){
        return new JsonResult(questionService.countQuestion(),State.SUCCESS ,"获取成功");
    }

    @GetMapping(value = "/getQuestionList/{page}/{uid}")
    public JsonResult getQuestionList(@PathVariable int page,@PathVariable long uid){
        page--;
        return new JsonResult(questionService.queryQuestionList(page,uid),State.SUCCESS ,"获取成功");
    }

    @PostMapping(value = "/searchQuestion/{page}/{search}")
    public JsonResult serachQuestion(@PathVariable int page,@PathVariable String search){
        page--;
        return new JsonResult(questionService.querySearchQuestionList(page,search),State.SUCCESS ,"查询成功");
    }




}
