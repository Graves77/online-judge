package com.example.service.question.impl;

import com.example.mapper.question.RecordMapper;
import com.example.model.judge.SubmitRecord;
import com.example.service.question.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;



    @Override
    public List<SubmitRecord> getSubmitRecordList(long uid, long qid, int page) {
        page--;
        return recordMapper.getSubmitRecordList(uid,qid,page);
    }

    @Override
    public List<SubmitRecord> getSubmitRecordList(long qid, int page) {
        page--;
        return recordMapper.getSubmitRecordListWithQid(qid,page);
    }

    @Override
    public List<SubmitRecord> getSubmitRecordListWithUid(long uid, int page) {
        page--;
        return recordMapper.getSubmitRecordListWithUid(uid,page);
    }


}
