package com.example.service.question.impl;

import com.example.mapper.question.RecordMapper;
import com.example.model.question.UserResolve;
import com.example.model.question.UserResolveDto;
import com.example.service.question.UserQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserQuestionServiceImpl implements UserQuestionService {

    @Autowired
    private RecordMapper recordMapper;

    /**
     * @param uid
     * @return
     */
    @Override
    public List<UserResolveDto> getUserResolve(long uid) {
        log.info("perd:{}",uid);
        UserResolve perDifficultySolve = recordMapper.getPerDifficultySolve(uid);
        log.info("perd:{}",perDifficultySolve);
        List<UserResolveDto> objects = new ArrayList<>();
        if(perDifficultySolve.getEasyResolve()!=0){
            objects.add(new UserResolveDto(perDifficultySolve.getEasyResolve(), "简单"));
        }
        if(perDifficultySolve.getMeddleResolve()!=0){
            objects.add(new UserResolveDto(perDifficultySolve.getMeddleResolve(), "中等"));
        }
        if(perDifficultySolve.getHardResolve()!=0){
            objects.add(new UserResolveDto(perDifficultySolve.getHardResolve(), "困难"));
        }
        if(perDifficultySolve.getNightmareResolve()!=0){
            objects.add(new UserResolveDto(perDifficultySolve.getNightmareResolve(), "噩梦"));
        }
        return objects;
    }

    /**
     * @param uid
     * @return
     */
    @Override
    public List<UserResolveDto> getUserResolveWithPercent(long uid) {
        UserResolve perDifficultySolve = recordMapper.getPerDifficultySolve(uid);

        int easy = recordMapper.countPerDifficultyCount("简单");
        int meddle = recordMapper.countPerDifficultyCount("中等");
        int hard = recordMapper.countPerDifficultyCount("困难");
        int nightmare = recordMapper.countPerDifficultyCount("噩梦");
        System.out.println("=============================================================== " + easy + " --- --- ---" + perDifficultySolve.getEasyResolve() / easy);

        List<UserResolveDto> objects = new ArrayList<>();
        objects.add(new UserResolveDto( Double.parseDouble(String.format("%.2f",(double)perDifficultySolve.getEasyResolve() / easy * 100)), "简单"));
        objects.add(new UserResolveDto( Double.parseDouble(String.format("%.2f",(double) perDifficultySolve.getMeddleResolve() / meddle * 100)), "中等"));
        objects.add(new UserResolveDto(Double.parseDouble(String.format("%.2f",(double) perDifficultySolve.getHardResolve() / hard * 100)), "困难"));
        objects.add(new UserResolveDto(Double.parseDouble(String.format("%.2f",(double) perDifficultySolve.getNightmareResolve() / nightmare * 100)), "噩梦"));
        return objects;
    }
}
