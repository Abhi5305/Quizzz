package com.exam.quiz.service.impl;


import com.exam.quiz.model.Category;
import com.exam.quiz.model.Quiz;
import com.exam.quiz.repo.QuizRepository;
import com.exam.quiz.service.CategoryService;
import com.exam.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private CategoryService categoryService;

    @Override
    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).get();
    }

    @Override
    public Quiz updateQuiz(Long id, Quiz quiz) {
        return quizRepository.findById(id)
                .map(existingQuiz -> {
                    existingQuiz.setTitle(quiz.getTitle());
                    existingQuiz.setDescription(quiz.getDescription());
                    existingQuiz.setTotalMarks(quiz.getTotalMarks());
                    existingQuiz.setNumberOfQuestions(quiz.getNumberOfQuestions());
                    existingQuiz.setStatus(quiz.isStatus());
                    return quizRepository.save(existingQuiz);
                })
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
    }

    @Override
    public void deleteQuiz(Long id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
        } else {
            throw new RuntimeException("Quiz not found with id: " + id);
        }
    }

    @Override
    public List<Quiz> getQuizzesOfCategory(Long id) {
        Category category = categoryService.getCategoryById(id);
        return category.getQuizzes().stream().toList();
    }
    @Override
    public List<Quiz> getQuizByStatus() {
        return quizRepository.findByStatus(true);
    }
    @Override
    public List<Quiz> getActiveQuizzesOfCategory(Long id) {
        Category category = categoryService.getCategoryById(id);
        return quizRepository.findByCategoryAndStatus(category,true);
    }

}
