package com.exam.quiz.repo;

import com.exam.quiz.model.Category;
import com.exam.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

    List<Quiz> findByCategory(Category category);
    List<Quiz> findByStatus(boolean status);
    List<Quiz> findByCategoryAndStatus(Category category, boolean status);
}
