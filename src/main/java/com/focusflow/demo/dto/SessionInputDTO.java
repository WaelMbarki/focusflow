package com.focusflow.demo.dto;

import com.focusflow.demo.model.SessionsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionInputDTO {
    private String taskId;               // nullable — optional link per spec
    private Integer plannedDurationSeconds;
    // no startTime — server sets it to now() the instant the session is created
    // no durationSeconds/status — server initializes these (0, ACTIVE)
}

