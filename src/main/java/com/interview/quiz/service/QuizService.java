package com.interview.quiz.service;

import com.interview.quiz.client.QuestionClient;
import com.interview.quiz.dto.QuizResponseDto;
import com.interview.quiz.dto.QuizStartResponseDto;
import com.interview.quiz.entity.Quiz;
import com.interview.quiz.entity.QuizQuestion;
import com.interview.quiz.repository.QuizQuestionRepository;
import com.interview.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuestionClient questionClient;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Long> questions = questionClient.getQuestionsForQuiz(category, numQ).getBody();
        
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setNumberOfQuestions(numQ);
        quiz.setCreatedAt(LocalDateTime.now());
        Quiz savedQuiz = quizRepository.save(quiz);

        if (questions != null) {
            for (Long questionId : questions) {
                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.setQuizId(savedQuiz.getId());
                quizQuestion.setQuestionId(questionId);
                quizQuestionRepository.save(quizQuestion);
            }
        }

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Object>> getQuizQuestions(Long id) {
        List<QuizQuestion> quizQuestions = quizQuestionRepository.findByQuizId(id);
        List<Long> questionIds = quizQuestions.stream()
                .map(QuizQuestion::getQuestionId)
                .collect(Collectors.toList());
        
        ResponseEntity<List<Object>> questions = questionClient.getQuestionsFromId(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Long id, List<Object> responses) {
        ResponseEntity<Integer> score = questionClient.getScore(responses);
        return score;
    }

    public ResponseEntity<List<QuizResponseDto>> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizResponseDto> quizDtos = quizzes.stream().map(quiz -> {
            List<QuizQuestion> quizQuestions = quizQuestionRepository.findByQuizId(quiz.getId());
            List<Long> questionIds = quizQuestions.stream()
                    .map(QuizQuestion::getQuestionId)
                    .collect(Collectors.toList());
            return new QuizResponseDto(quiz.getId(), quiz.getTitle(), quiz.getNumberOfQuestions(), quiz.getCreatedAt(), questionIds);
        }).collect(Collectors.toList());
        
        return new ResponseEntity<>(quizDtos, HttpStatus.OK);
    }

    public ResponseEntity<QuizResponseDto> getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        List<QuizQuestion> quizQuestions = quizQuestionRepository.findByQuizId(id);
        List<Long> questionIds = quizQuestions.stream()
                .map(QuizQuestion::getQuestionId)
                .collect(Collectors.toList());
        
        QuizResponseDto quizDto = new QuizResponseDto(quiz.getId(), quiz.getTitle(), quiz.getNumberOfQuestions(), quiz.getCreatedAt(), questionIds);
        return new ResponseEntity<>(quizDto, HttpStatus.OK);
    }

    public ResponseEntity<QuizStartResponseDto> startQuiz(String title, Integer numberOfQuestions) {
        List<Long> questionIds = questionClient.getRandomQuestions(numberOfQuestions).getBody();
        
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setNumberOfQuestions(numberOfQuestions);
        quiz.setCreatedAt(LocalDateTime.now());
        Quiz savedQuiz = quizRepository.save(quiz);

        if (questionIds != null) {
            for (Long questionId : questionIds) {
                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.setQuizId(savedQuiz.getId());
                quizQuestion.setQuestionId(questionId);
                quizQuestionRepository.save(quizQuestion);
            }
        }

        List<Object> questions = questionClient.getQuestionsFromId(questionIds).getBody();
        QuizStartResponseDto responseDto = new QuizStartResponseDto(savedQuiz.getId(), questions);
        
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
