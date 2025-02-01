package com.exam.quiz.exceptions;

import com.exam.quiz.dto.ErrorList;
import com.exam.quiz.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorList> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMsg = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errorMsg.add(errorMessage);
        });
        ErrorList errorList = buildErrorResponse(1005,errorMsg);
        return new ResponseEntity<>(errorList,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorList> handleUserAlreadyPresentException(UserAlreadyExistsException ex) {
        List<String> errorMsg = new ArrayList<>();
        if(ex.getMessage().contains("email")){
            errorMsg.add("email already exists. Try another!");
        }else{
            errorMsg.add("username already exists. Try another!");
        }
        ErrorList errorList = buildErrorResponse(1002,errorMsg);
        return new ResponseEntity<>(errorList,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorList> handleUsernamePassword(UsernameNotFoundException ex) {
        List<String> errorMsg = new ArrayList<>();
        ErrorList errorList = buildErrorResponse(1003,errorMsg);
        return new ResponseEntity<>(errorList,HttpStatus.BAD_REQUEST);
    }
    public ErrorList buildErrorResponse(int errorCode, List<String> errorMessage){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(errorCode);
        errorResponse.setErrorMessage(errorMessage);
        ErrorList errorList = new ErrorList();
        List<ErrorResponse> errorResponseList = new ArrayList<>();
        errorResponseList.add(errorResponse);
        errorList.setErrors(errorResponseList);
        return errorList;
    }
}
