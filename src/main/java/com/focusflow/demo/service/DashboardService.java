package com.focusflow.demo.service;

import com.focusflow.demo.dto.DailyFocusPointDTO;
import com.focusflow.demo.dto.DashbordSummaryDTO;
import com.focusflow.demo.model.DailyGoal;
import com.focusflow.demo.model.FocusSession;
import com.focusflow.demo.model.Task;
import com.focusflow.demo.model.TaskStatus;
import com.focusflow.demo.repository.DailyGoalRepository;
import com.focusflow.demo.repository.FocusSessionRepository;
import com.focusflow.demo.repository.TaskRepository;
import com.focusflow.demo.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    FocusSessionRepository focusSessionRepository;
    @Autowired
    DailyGoalRepository dailyGoalRepository;
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;
    @Autowired
    FocusSessionService focusSessionService;
    @Autowired
    DailyGoalService dailyGoalService;

    public DashbordSummaryDTO getSummary() {
        String currentUserId = SecurityUtils.getCurrentUserId();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        List<Task> allTasks = taskRepository.findByUser_Id(currentUserId);
        List<FocusSession> allSessions = focusSessionRepository.findByUser_Id(currentUserId);
        List<DailyGoal> todaysGoals = dailyGoalRepository.findByUser_IdAndGoalDate(currentUserId, today);

        int tasksCompletedToday = countCompletedOn(allTasks, today);
        int tasksCompletedYesterday = countCompletedOn(allTasks, yesterday);

        int focusSecondsToday = sumFocusSecondsOn(allSessions, today);
        int focusSecondsYesterday = sumFocusSecondsOn(allSessions, yesterday);

        int currentStreak = focusSessionService.getCurrentStreak();

        double goalCompletionPercentage = calculateGoalCompletion(todaysGoals);

        DashbordSummaryDTO summary = new DashbordSummaryDTO();
        summary.setTasksCompletedToday(tasksCompletedToday);
        summary.setTasksCompletedYesterday(tasksCompletedYesterday);
        summary.setFocusSecondsToday(focusSecondsToday);
        summary.setFocusSecondsYesterday(focusSecondsYesterday);
        summary.setCurrentStreakDays(currentStreak);
        summary.setDailyGoalCompletionPercentage(goalCompletionPercentage);

        summary.setTodaysTasks(allTasks.stream()
                .filter(t -> t.getDueDate() != null && t.getDueDate().toLocalDate().equals(today))
                .map(taskService::toDTO)
                .toList());

        summary.setUpcomingTasks(allTasks.stream()
                .filter(t -> t.getDueDate() != null && t.getDueDate().toLocalDate().isAfter(today))
                .map(taskService::toDTO)
                .toList());

        summary.setTodaysGoals(todaysGoals.stream()
                .map(dailyGoalService::toDTO)
                .toList());

        summary.setWeeklyFocus(buildWeeklyFocus(allSessions, today));
        summary.setWeeklyTasksCompleted(buildWeeklyTaskCounts(allTasks, today));

        return summary;
    }

    private int countCompletedOn(List<Task> tasks, LocalDate day) {
        return (int) tasks.stream()
                .filter(t -> t.getCompletedAt() != null && t.getCompletedAt().toLocalDate().equals(day))
                .count();
    }

    private int sumFocusSecondsOn(List<FocusSession> sessions, LocalDate day) {
        return sessions.stream()
                .filter(s -> s.getStartTime().toLocalDate().equals(day))
                .mapToInt(FocusSession::getDurationSeconds)
                .sum();
    }

    private double calculateGoalCompletion(List<DailyGoal> goals) {
        if (goals.isEmpty()) {
            return 0.0;
        }
        long completedCount = goals.stream().filter(DailyGoal::getCompleted).count();
        return (completedCount * 100.0) / goals.size();
    }

    private List<DailyFocusPointDTO> buildWeeklyFocus(List<FocusSession> sessions, LocalDate today) {
        List<DailyFocusPointDTO> points = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            int seconds = sumFocusSecondsOn(sessions, day);
            double hours = seconds / 3600.0;
            points.add(new DailyFocusPointDTO(day, hours));
        }
        return points;
    }

    private List<DailyFocusPointDTO> buildWeeklyTaskCounts(List<Task> tasks, LocalDate today) {
        List<DailyFocusPointDTO> points = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            int count = countCompletedOn(tasks, day);
            points.add(new DailyFocusPointDTO(day, (double) count));
        }
        return points;
    }
}