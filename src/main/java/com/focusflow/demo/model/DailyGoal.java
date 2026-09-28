package com.focusflow.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="daily_goals")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyGoal
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private String title;
    @Enumerated(EnumType.STRING)
    private GoalType goalType;
    private Integer targetValue;
    private Integer currentValue;
    private LocalDate goalDate;
    private Boolean completed;
}
