package com.exam.quiz.service;

import com.exam.quiz.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question createQuestion(Question question);
    List<Question> getAllQuestions();
    Optional<Question> getQuestionById(Long id);
    Question updateQuestion(Long id, Question question);
    void deleteQuestion(Long id);
    List<Question> getQuestionsOfQuiz(Long quizId);
}
