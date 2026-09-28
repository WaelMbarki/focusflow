package com.focusflow.demo.repository;

import com.focusflow.demo.model.DailyGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyGoalRepository extends JpaRepository<DailyGoal,String>
{
    // Today's Goals — dashboard widget
    List<DailyGoal> findByUser_IdAndGoalDate(String userId, LocalDate goalDate);

    // All goals for a user (history / analytics)
    List<DailyGoal> findByUser_Id(String userId);

}
