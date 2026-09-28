package com.focusflow.demo.dto;

import com.focusflow.demo.model.SessionsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionResponseDTO
{
    private String id;
    private String taskId;      // nullable
    private String taskTitle;   // nullable — inline, same reasoning as project name above
    private LocalDateTime startTime;
    private Integer plannedDurationSeconds;
    private Integer durationSeconds;
    private SessionsStatus status;

}
