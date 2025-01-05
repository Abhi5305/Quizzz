package com.exam.quiz.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message){
        super(message);
    }
}
