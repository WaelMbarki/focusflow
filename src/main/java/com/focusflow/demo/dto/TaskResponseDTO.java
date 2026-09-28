package com.focusflow.demo.dto;

import com.focusflow.demo.model.TaskPriority;
import com.focusflow.demo.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskResponseDTO
{
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private Integer estimatedDurationMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String projectId;
    private String projectName;
    private String projectColor;

}
