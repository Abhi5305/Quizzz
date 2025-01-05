package com.exam.quiz.util;

import com.exam.quiz.dto.TokenResponse;
import com.exam.quiz.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseGenerator {
    public UserResponse createResponse(String message){
        UserResponse userResponse = new UserResponse();
        userResponse.setMessage(message);
        return userResponse;
    }
    public TokenResponse createTokenResponse(String token){
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        return tokenResponse;
    }
}
