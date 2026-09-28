package com.focusflow.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private Integer estimatedDurationMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

}
