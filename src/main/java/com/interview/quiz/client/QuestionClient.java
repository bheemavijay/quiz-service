package com.interview.quiz.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "question-service", url = "http://localhost:8082")
public interface QuestionClient {

    @GetMapping("question/generate")
    ResponseEntity<List<Long>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions);

    @PostMapping("question/getQuestions")
    ResponseEntity<List<Object>> getQuestionsFromId(@RequestBody List<Long> questionIds);

    @PostMapping("question/getScore")
    ResponseEntity<Integer> getScore(@RequestBody List<Object> responses);

    @GetMapping("questions/random")
    ResponseEntity<List<Long>> getRandomQuestions(@RequestParam Integer limit);
}
