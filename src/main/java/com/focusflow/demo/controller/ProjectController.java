package com.focusflow.demo.controller;

import com.focusflow.demo.dto.ProjectInputDTO;
import com.focusflow.demo.dto.ProjectResponseDTO;
import com.focusflow.demo.model.Project;
import com.focusflow.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController
{
    @Autowired
    ProjectService projectService;
    @GetMapping("/getProject")
    public ResponseEntity<ProjectResponseDTO> getProject(@RequestParam String id)
    {
        return ResponseEntity.ok(projectService.getProject(id));
    }
    @GetMapping("/getProjects")
    public ResponseEntity<List<ProjectResponseDTO>> getProjects() {
        return ResponseEntity.ok(projectService.getProjects());
    }
    @PostMapping("/createProject")
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectInputDTO project)
    {
        return ResponseEntity.ok(projectService.create(project));
    }
    @GetMapping("/deleteproject")
    public ResponseEntity<String> deleteProject(@RequestParam String id)
    {
        return ResponseEntity.ok(projectService.delete(id));
    }
}
