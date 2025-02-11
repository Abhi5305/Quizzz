package com.exam.quiz.service;

import com.exam.quiz.model.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    Quiz createQuiz(Quiz quiz);
    List<Quiz> getAllQuizzes();
    Quiz getQuizById(Long id);
    Quiz updateQuiz(Long id, Quiz quiz);
    void deleteQuiz(Long id);
    List<Quiz> getQuizzesOfCategory(Long id);
    List<Quiz> getQuizByStatus();
    List<Quiz> getActiveQuizzesOfCategory(Long id);
}

