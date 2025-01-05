package com.exam.quiz.dto;

import java.util.List;

public class ErrorList {
    private List<ErrorResponse> errors;

    public List<ErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorResponse> errors) {
        this.errors = errors;
    }
}
