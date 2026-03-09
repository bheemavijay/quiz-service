package com.interview.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponseDto {
    private Long id;
    private String title;
    private Integer numberOfQuestions;
    private LocalDateTime createdAt;
    private List<Long> questionIds;
}
