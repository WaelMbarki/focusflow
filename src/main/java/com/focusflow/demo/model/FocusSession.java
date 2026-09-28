package com.focusflow.demo.model;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="focus_sessions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FocusSession
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;
    private LocalDateTime startTime;
    @Enumerated(EnumType.STRING)
    private SessionsStatus status;

    private Integer plannedDurationSeconds;
    private Integer durationSeconds;
}
