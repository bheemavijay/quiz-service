package com.interview.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private String title;
    private Integer numberOfQuestions;
    private String categoryName; // Assuming we still need category to fetch questions
}
