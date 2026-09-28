package com.focusflow.demo.service;

import com.focusflow.demo.dto.SessionInputDTO;
import com.focusflow.demo.dto.SessionResponseDTO;
import com.focusflow.demo.exception.ResourceNotFoundException;
import com.focusflow.demo.model.FocusSession;
import com.focusflow.demo.model.SessionsStatus;
import com.focusflow.demo.model.Task;
import com.focusflow.demo.model.User;
import com.focusflow.demo.repository.FocusSessionRepository;
import com.focusflow.demo.repository.TaskRepository;
import com.focusflow.demo.repository.UserRepository;
import com.focusflow.demo.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FocusSessionService {

    @Autowired
    FocusSessionRepository focusSessionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;

    public SessionResponseDTO create(SessionInputDTO sessionInput) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = null;
        if (sessionInput.getTaskId() != null) {
            task = taskRepository.findById(sessionInput.getTaskId())
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
            if (!task.getUser().getId().equals(currentUserId)) {
                throw new ResourceNotFoundException("Task not found");
            }
        }

        FocusSession session = new FocusSession();
        session.setUser(user);
        session.setTask(task);
        session.setStartTime(LocalDateTime.now());
        session.setPlannedDurationSeconds(sessionInput.getPlannedDurationSeconds());
        session.setDurationSeconds(0);
        session.setStatus(SessionsStatus.ACTIVE);

        FocusSession saved = focusSessionRepository.save(session);
        return toDTO(saved);
    }

    public String delete(String id) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        FocusSession session = focusSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        if (!session.getUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Session not found");
        }
        focusSessionRepository.delete(session);
        return "deleted";
    }

    public List<SessionResponseDTO> getSessions() {
        String currentUserId = SecurityUtils.getCurrentUserId();
        List<FocusSession> sessions = focusSessionRepository.findByUser_Id(currentUserId);
        return sessions.stream().map(this::toDTO).toList();
    }

    public SessionResponseDTO getSession(String id) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        FocusSession session = focusSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        if (!session.getUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Session not found");
        }
        return toDTO(session);
    }

    public SessionResponseDTO toDTO(FocusSession session) {
        Task task = session.getTask();
        String taskId = task != null ? task.getId() : null;
        String taskTitle = task != null ? task.getTitle() : null;

        return new SessionResponseDTO(
                session.getId(),
                taskId,
                taskTitle,
                session.getStartTime(),
                session.getPlannedDurationSeconds(),
                session.getDurationSeconds(),
                session.getStatus()
        );
    }
}