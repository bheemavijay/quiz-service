package com.interview.quiz.controller;

import com.interview.quiz.dto.QuizDto;
import com.interview.quiz.dto.QuizResponseDto;
import com.interview.quiz.dto.QuizStartRequestDto;
import com.interview.quiz.dto.QuizStartResponseDto;
import com.interview.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumberOfQuestions(), quizDto.getTitle());
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> getQuizById(@PathVariable Long quizId){
        return quizService.getQuizById(quizId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuizResponseDto>> getAllQuizzes(){
        return quizService.getAllQuizzes();
    }

    @PostMapping("/start")
    public ResponseEntity<QuizStartResponseDto> startQuiz(@RequestBody QuizStartRequestDto requestDto){
        return quizService.startQuiz(requestDto.getTitle(), requestDto.getNumberOfQuestions());
    }
}
