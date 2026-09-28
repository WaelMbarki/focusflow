package com.focusflow.demo.repository;

import com.focusflow.demo.model.FocusSession;
import com.focusflow.demo.model.Task;
import jakarta.websocket.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FocusSessionRepository extends JpaRepository<FocusSession,String>
{
    // Weekly Focus chart — all sessions for a user within a date range
    List<FocusSession> findByUser_IdAndStartTimeBetween(String userId, LocalDateTime start, LocalDateTime end);

    // Focus time spent on one specific task
    List<FocusSession> findByUser_IdAndTask_Id(String userId, String taskId);

    // The user's currently running/paused session, if any (dashboard timer state)
    Optional<FocusSession> findByUser_IdAndStatus(String userId, com.focusflow.demo.model.SessionsStatus status);
    List<FocusSession> findByUser_Id(String userId);

}
