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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        session.setLastResumedAt(session.getStartTime());
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
    public SessionResponseDTO pause(String id) {
        FocusSession session = findOwnedSession(id);
        if (session.getStatus() != SessionsStatus.ACTIVE) {
            throw new IllegalStateException("Only an active session can be paused");
        }
        addElapsedTime(session);
        session.setStatus(SessionsStatus.PAUSED);
        session.setLastResumedAt(null);
        return toDTO(focusSessionRepository.save(session));
    }

    public SessionResponseDTO resume(String id) {
        FocusSession session = findOwnedSession(id);
        if (session.getStatus() != SessionsStatus.PAUSED) {
            throw new IllegalStateException("Only a paused session can be resumed");
        }
        session.setLastResumedAt(LocalDateTime.now());
        session.setStatus(SessionsStatus.ACTIVE);
        return toDTO(focusSessionRepository.save(session));
    }

    public SessionResponseDTO complete(String id) {
        FocusSession session = findOwnedSession(id);
        if (session.getStatus() == SessionsStatus.ACTIVE) {
            addElapsedTime(session);
        } else if (session.getStatus() != SessionsStatus.PAUSED) {
            throw new IllegalStateException("This session is already finished");
        }
        session.setStatus(SessionsStatus.COMPLETED);
        session.setLastResumedAt(null);
        return toDTO(focusSessionRepository.save(session));
    }

    private void addElapsedTime(FocusSession session) {
        long elapsed = Duration.between(session.getLastResumedAt(), LocalDateTime.now()).getSeconds();
        session.setDurationSeconds(session.getDurationSeconds() + (int) elapsed);
    }

    private FocusSession findOwnedSession(String id) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        FocusSession session = focusSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        if (!session.getUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Session not found");
        }
        return session;
    }
    public int getCurrentStreak() {
        String currentUserId = SecurityUtils.getCurrentUserId();
        List<FocusSession> sessions = focusSessionRepository.findByUser_Id(currentUserId);

        Set<LocalDate> activeDays = sessions.stream()
                .map(session -> session.getStartTime().toLocalDate())
                .collect(Collectors.toSet());

        LocalDate day = activeDays.contains(LocalDate.now())
                ? LocalDate.now()
                : LocalDate.now().minusDays(1);

        int streak = 0;
        while (activeDays.contains(day)) {
            streak++;
            day = day.minusDays(1);
        }
        return streak;
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