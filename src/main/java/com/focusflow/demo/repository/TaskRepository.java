package com.focusflow.demo.repository;

import com.focusflow.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,String>
{
    // "Today's Tasks" — tasks for this user due on a specific date
    List<Task> findByUser_IdAndDueDateBetween(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    // "Upcoming Tasks" — tasks due after now, within some future window
    List<Task> findByUser_IdAndDueDateAfter(String userId, LocalDateTime now);

    // Filter by project
    List<Task> findByUser_IdAndProject_Id(String userId, String projectId);

    // Search tasks by title (case-insensitive, partial match)
    List<Task> findByUser_IdAndTitleContainingIgnoreCase(String userId, String title);

    // All tasks for a user (base listing / further filtering in service layer)
    List<Task> findByUser_Id(String userId);

}
