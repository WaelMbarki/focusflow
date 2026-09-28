package com.focusflow.demo.dto;

import com.focusflow.demo.model.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoalsInputDTO {
    private String title;
    private GoalType type;
    private Integer targetValue;
    private LocalDate goalDate;   // LocalDate, not LocalDateTime — a goal belongs to a day, not a moment
}