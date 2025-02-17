package com.exam.quiz.controller;

import com.exam.quiz.model.Category;
import com.exam.quiz.model.Quiz;
import com.exam.quiz.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping
    @Operation(summary = "Add a quiz", tags = {"Quiz Management"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz createdQuiz = quizService.createQuiz(quiz);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Fetch fetch all quizzes", tags = {"Quiz Management"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetch a quiz by id", tags = {"Quiz Management"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a quiz by id", tags = {"Quiz Management"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {
        try {
            Quiz updatedQuiz = quizService.updateQuiz(id, quiz);
            return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Provide an id and delete", tags = {"Quiz Management"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        try {
            quizService.deleteQuiz(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/category/{id}")
    @Operation(summary = "Provide the category id get all the associated quizzes", tags = {"Quiz Management"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Quiz>> getQuizzesOfCategory(@PathVariable Long id) {
        List<Quiz> quizzes = quizService.getQuizzesOfCategory(id);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/status")
    @Operation(summary = "Fetch all the active quizzes", tags = {"Quiz Management"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<Quiz> getQuizByStatus() {
        return quizService.getQuizByStatus();
    }
    @GetMapping("/active/category/{id}")
    @Operation(summary = "Get all the active quizzes of a category", tags = {"Quiz Management"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<Quiz> getActiveQuizzesOfCategory(@PathVariable Long id) {
        return quizService.getActiveQuizzesOfCategory(id);
    }

}
