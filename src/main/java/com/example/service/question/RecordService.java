package com.example.service.question;


import com.example.model.judge.SubmitRecord;

import java.util.List;

public interface RecordService {

    List<SubmitRecord> getSubmitRecordList(long uid, long qid, int page);

    List<SubmitRecord> getSubmitRecordList(long qid,int page);

    List<SubmitRecord> getSubmitRecordListWithUid(long uid,int page);


}
