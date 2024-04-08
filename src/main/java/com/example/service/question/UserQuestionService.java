package com.example.service.question;


import com.example.model.question.UserResolveDto;

import java.util.List;

public interface UserQuestionService {

    List<UserResolveDto> getUserResolve(long uid);

    List<UserResolveDto> getUserResolveWithPercent(long uid);
}
