package com.focusflow.demo.dto;

import com.focusflow.demo.model.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalResponseDTO {
    private String id;
    private String title;
    private GoalType type;
    private Integer targetValue;
    private Integer currentValue;
    private LocalDate goalDate;
    private Boolean completed;
}