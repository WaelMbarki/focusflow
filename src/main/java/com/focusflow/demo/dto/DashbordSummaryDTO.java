package com.focusflow.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashbordSummaryDTO
{
    private Integer tasksCompletedToday;
    private Integer tasksCompletedYesterday; // for the "+3 from yesterday" delta text
    private Integer focusSecondsToday;
    private Integer focusSecondsYesterday;
    private Integer currentStreakDays;
    private Double dailyGoalCompletionPercentage;

    private List<TaskResponseDTO> todaysTasks;
    private List<TaskResponseDTO> upcomingTasks;
    private List<GoalResponseDTO> todaysGoals;

    // one entry per day of the current week, for the weekly focus bar chart
    private List<DailyFocusPointDTO> weeklyFocus;

}
