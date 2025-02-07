package com.exam.quiz.service.impl;

import com.exam.quiz.model.Question;
import com.exam.quiz.model.Quiz;
import com.exam.quiz.repo.QuestionRepository;
import com.exam.quiz.service.QuestionService;
import com.exam.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizService quizService;

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question updateQuestion(Long id, Question question) {
        return questionRepository.findById(id)
                .map(existingQuestion -> {
                    existingQuestion.setQuestionText(question.getQuestionText());
                    existingQuestion.setOption1(question.getOption1());
                    existingQuestion.setOption2(question.getOption2());
                    existingQuestion.setOption3(question.getOption3());
                    existingQuestion.setOption4(question.getOption4());
                    existingQuestion.setCorrectAnswer(question.getCorrectAnswer());
                    return questionRepository.save(existingQuestion);
                })
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    @Override
    public void deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Question not found with id: " + id);
        }
    }

    @Override
    public List<Question> getQuestionsOfQuizByNumberOfQuestions(Long quizId) {
        Quiz quiz = quizService.getQuizById(quizId);
        List<Question> questions = quiz.getQuestions().stream().toList();
        //compare the no of questions  count/size(list) vs provided if found bigger shorten the list with provided no.
        if(questions.size()>quiz.getNumberOfQuestions()){
            questions = questions.subList(0, quiz.getNumberOfQuestions()+1);
        }
        Collections.shuffle(questions);
        return questions;
    }

    @Override
    public List<Question> getAllQuestionsOfQuiz(Long quizId) {
        Quiz quiz = quizService.getQuizById(quizId);
        return quiz.getQuestions().stream().toList();
    }
}
