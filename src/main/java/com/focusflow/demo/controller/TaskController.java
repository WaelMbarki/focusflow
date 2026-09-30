package com.focusflow.demo.controller;

import com.focusflow.demo.dto.TaskInputDTO;
import com.focusflow.demo.dto.TaskResponseDTO;
import com.focusflow.demo.repository.UserRepository;
import com.focusflow.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController
{
    @Autowired
    TaskService taskService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<TaskResponseDTO> create(@RequestBody  TaskInputDTO task)
    {
        return ResponseEntity.ok(taskService.create(task));
    }
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String id)
    {
        return ResponseEntity.ok(taskService.delete(id));
    }
    @GetMapping("/getTasks")
    public ResponseEntity<List<TaskResponseDTO>> getTasks()
    {
        return ResponseEntity.ok(taskService.getTasks());
    }
    @GetMapping("/getTask")
    public ResponseEntity<TaskResponseDTO> getTask(@RequestParam String id)
    {
        return ResponseEntity.ok(taskService.getTask(id));
    }
    @PutMapping("/update")
    public ResponseEntity<TaskResponseDTO> update(@RequestParam String id, @RequestBody TaskInputDTO task) {
        return ResponseEntity.ok(taskService.update(id, task));
    }
}
