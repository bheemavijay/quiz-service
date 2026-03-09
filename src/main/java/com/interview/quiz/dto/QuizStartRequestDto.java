package com.interview.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizStartRequestDto {
    private String title;
    private Integer numberOfQuestions;
}
