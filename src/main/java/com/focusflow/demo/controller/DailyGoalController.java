package com.focusflow.demo.controller;

import com.focusflow.demo.dto.GoalResponseDTO;
import com.focusflow.demo.dto.GoalsInputDTO;
import com.focusflow.demo.service.DailyGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class DailyGoalController {

    @Autowired
    DailyGoalService dailyGoalService;

    @PostMapping("/create")
    public ResponseEntity<GoalResponseDTO> create(@RequestBody GoalsInputDTO goal) {
        return ResponseEntity.ok(dailyGoalService.create(goal));
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String id) {
        return ResponseEntity.ok(dailyGoalService.delete(id));
    }

    @GetMapping("/getGoals")
    public ResponseEntity<List<GoalResponseDTO>> getGoals() {
        return ResponseEntity.ok(dailyGoalService.getGoals());
    }

    @GetMapping("/getGoal")
    public ResponseEntity<GoalResponseDTO> getGoal(@RequestParam String id) {
        return ResponseEntity.ok(dailyGoalService.getGoal(id));
    }
}