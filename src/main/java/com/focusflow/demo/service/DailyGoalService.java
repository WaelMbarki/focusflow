package com.focusflow.demo.service;

import com.focusflow.demo.dto.GoalResponseDTO;
import com.focusflow.demo.dto.GoalsInputDTO;
import com.focusflow.demo.exception.ResourceNotFoundException;
import com.focusflow.demo.model.DailyGoal;
import com.focusflow.demo.model.User;
import com.focusflow.demo.repository.DailyGoalRepository;
import com.focusflow.demo.repository.UserRepository;
import com.focusflow.demo.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyGoalService {

    @Autowired
    DailyGoalRepository dailyGoalRepository;
    @Autowired
    UserRepository userRepository;

    public GoalResponseDTO create(GoalsInputDTO goalInput) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        DailyGoal goal = new DailyGoal();
        goal.setUser(user);
        goal.setTitle(goalInput.getTitle());
        goal.setGoalType(goalInput.getType());
        goal.setTargetValue(goalInput.getTargetValue());
        goal.setCurrentValue(0);
        goal.setGoalDate(goalInput.getGoalDate());
        goal.setCompleted(false);

        DailyGoal saved = dailyGoalRepository.save(goal);
        return toDTO(saved);
    }

    public String delete(String id) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        DailyGoal goal = dailyGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
        if (!goal.getUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Goal not found");
        }
        dailyGoalRepository.delete(goal);
        return "deleted";
    }

    public List<GoalResponseDTO> getGoals() {
        String currentUserId = SecurityUtils.getCurrentUserId();
        List<DailyGoal> goals = dailyGoalRepository.findByUser_Id(currentUserId);
        return goals.stream().map(this::toDTO).toList();
    }

    public GoalResponseDTO getGoal(String id) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        DailyGoal goal = dailyGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
        if (!goal.getUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Goal not found");
        }
        return toDTO(goal);
    }
    private DailyGoal findOwnedGoal(String id) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        DailyGoal goal = dailyGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
        if (!goal.getUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Goal not found");
        }
        return goal;
    }

    public GoalResponseDTO update(String id, GoalsInputDTO goalInput) {
        DailyGoal goal = findOwnedGoal(id);

        if (goalInput.getTitle() != null) {
            goal.setTitle(goalInput.getTitle());
        }
        if (goalInput.getType() != null) {
            goal.setGoalType(goalInput.getType());
        }
        if (goalInput.getTargetValue() != null) {
            goal.setTargetValue(goalInput.getTargetValue());
        }
        if (goalInput.getGoalDate() != null) {
            goal.setGoalDate(goalInput.getGoalDate());
        }

        return toDTO(dailyGoalRepository.save(goal));
    }
    public GoalResponseDTO toDTO(DailyGoal goal) {
        return new GoalResponseDTO(
                goal.getId(),          // String ✓
                goal.getTitle(),       // String ✓
                goal.getGoalType(),    // GoalType ✓
                goal.getTargetValue(), // Integer ✓
                goal.getCurrentValue(),// Integer ✓
                goal.getGoalDate(),    // LocalDate ✓
                goal.getCompleted()    // Boolean ✓
        );    }
}