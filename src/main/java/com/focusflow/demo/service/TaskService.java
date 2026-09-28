package com.focusflow.demo.service;

import com.focusflow.demo.dto.ProjectResponseDTO;
import com.focusflow.demo.dto.TaskInputDTO;
import com.focusflow.demo.dto.TaskResponseDTO;
import com.focusflow.demo.exception.ResourceNotFoundException;
import com.focusflow.demo.model.Project;
import com.focusflow.demo.model.Task;
import com.focusflow.demo.model.User;
import com.focusflow.demo.repository.ProjectRepository;
import com.focusflow.demo.repository.TaskRepository;
import com.focusflow.demo.repository.UserRepository;
import com.focusflow.demo.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService
{
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;

    public TaskResponseDTO create(TaskInputDTO taskInput) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Project project = null;
        if (taskInput.getProjectId() != null) {
            project = projectRepository.findById(taskInput.getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
            if (!project.getUser().getId().equals(currentUserId)) {
                throw new ResourceNotFoundException("Project not found");
            }
        }

        Task task = new Task();
        task.setTitle(taskInput.getTitle());
        task.setUser(user);
        task.setDescription(taskInput.getDescription());
        task.setPriority(taskInput.getPriority());
        task.setEstimatedDurationMinutes(taskInput.getEstimatedDurationMinutes());
        task.setDueDate(taskInput.getDueDate());
        task.setStatus(taskInput.getStatus());
        task.setCreatedAt(LocalDateTime.now());
        task.setProject(project);

        Task saved = taskRepository.save(task);
        return toDTO(saved);
    }
    public String delete(String id) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (!currentUserId.equals(task.getUser().getId())) {
            throw new ResourceNotFoundException("Task not found");
        }
        taskRepository.delete(task);
        return "deleted";
    }
    public List<TaskResponseDTO> getTasks()
    {
        String currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Task> tasks = taskRepository.findByUser_Id(user.getId());
        return tasks.stream()
                .map(this::toDTO)
                .toList();

    }
    public TaskResponseDTO getTask(String id)
    {
        String currentUserId = SecurityUtils.getCurrentUserId();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (!task.getUser().getId().equals(currentUserId)) {
        throw new ResourceNotFoundException("Task not found");
    }
    return toDTO(task);
}
    public TaskResponseDTO toDTO(Task task) {
        Project project = task.getProject();
        String projectId = project != null ? project.getId() : null;
        String projectName = project != null ? project.getName() : null;
        String projectColor = project != null ? project.getColor() : null;

        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority(),
                task.getStartDate(), task.getDueDate(), task.getEstimatedDurationMinutes(), task.getCreatedAt(), task.getCompletedAt(),
                projectId, projectName, projectColor);
    }
}
