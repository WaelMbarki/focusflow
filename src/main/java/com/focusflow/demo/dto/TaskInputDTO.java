package com.focusflow.demo.dto;

import com.focusflow.demo.model.TaskPriority;
import com.focusflow.demo.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskInputDTO {
    private String title;
    private String description;
    private TaskStatus status;       // still input-able: user can create a task directly as IN_PROGRESS if they want
    private TaskPriority priority;
    private LocalDateTime dueDate;
    private Integer estimatedDurationMinutes;
    private String projectId;        // nullable — task may not belong to a project
}