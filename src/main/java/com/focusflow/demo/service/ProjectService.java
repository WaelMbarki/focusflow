package com.focusflow.demo.service;

import com.focusflow.demo.dto.ProjectInputDTO;
import com.focusflow.demo.dto.ProjectResponseDTO;
import com.focusflow.demo.dto.UserDTO;
import com.focusflow.demo.exception.ResourceNotFoundException;
import com.focusflow.demo.model.Project;
import com.focusflow.demo.model.User;
import com.focusflow.demo.repository.ProjectRepository;
import com.focusflow.demo.repository.UserRepository;
import com.focusflow.demo.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    public ProjectResponseDTO create(ProjectInputDTO projectInput)
    {
        String currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Project project = new Project();
        project.setColor(projectInput.getColor());
        project.setName(projectInput.getName());
        project.setUser(user);
        Project saved = projectRepository.save(project);
        return toDTO(saved);
    }
    public String delete(String id) {
        String currentUserId = SecurityUtils.getCurrentUserId();

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (!project.getUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Project not found");
        }

        projectRepository.delete(project);
        return "deleted";
    }
    public ProjectResponseDTO getProject(String id) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        if (!project.getUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Project not found");
        }
        return toDTO(project);
    }
    public List<ProjectResponseDTO> getProjects()
    {
        String currentUserId = SecurityUtils.getCurrentUserId();
        List<Project> projects = projectRepository.findByUser_Id(currentUserId);
        return projects.stream()
                .map(this::toDTO)
                .toList();
    }
    private Project findOwnedProject(String id) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        if (!project.getUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Project not found");
        }
        return project;
    }

    public ProjectResponseDTO update(String id, ProjectInputDTO projectInput) {
        Project project = findOwnedProject(id);

        if (projectInput.getName() != null) {
            project.setName(projectInput.getName());
        }
        if (projectInput.getColor() != null) {
            project.setColor(projectInput.getColor());
        }

        return toDTO(projectRepository.save(project));
    }
    public ProjectResponseDTO toDTO(Project project)
    {
        return new ProjectResponseDTO(project.getId(),project.getColor(),project.getName());
    }


}