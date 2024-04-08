package com.example.controller.question;


import com.example.model.JsonResult;
import com.example.service.question.RecordService;
import com.example.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 凯威
 */
@RestController
public class RecordController {

    @Autowired
    private RecordService recordService;
    

    @GetMapping("/getSubmitRecords/{uid}/{qid}/{page}")
    public JsonResult getSubmitRecordsWithUidAndQid(@PathVariable long uid,@PathVariable long qid,@PathVariable int page){
        return new JsonResult(recordService.getSubmitRecordList(uid,qid,page),State.SUCCESS,"获取成功!");
    }
    @GetMapping("/getSubmitRecordsWithQid/{qid}/{page}")
    public JsonResult getSubmitRecordsWithQid(@PathVariable long qid,@PathVariable int page){
        return new JsonResult(recordService.getSubmitRecordList(qid,page),State.SUCCESS,"获取成功!");
    }

    @GetMapping("/getSubmitRecordsWithUid/{uid}/{page}")
    public JsonResult getSubmitRecordsWithUid(@PathVariable long uid,@PathVariable int page){
        return new JsonResult(recordService.getSubmitRecordListWithUid(uid,page),State.SUCCESS,"获取成功!");
    }



}
