package com.focusflow.demo.controller;

import com.focusflow.demo.dto.SessionInputDTO;
import com.focusflow.demo.dto.SessionResponseDTO;
import com.focusflow.demo.model.FocusSession;
import com.focusflow.demo.repository.FocusSessionRepository;
import com.focusflow.demo.security.SecurityUtils;
import com.focusflow.demo.service.FocusSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sessions")
public class FocusSessionController {

    @Autowired
    FocusSessionService focusSessionService;

    @PostMapping("/create")
    public ResponseEntity<SessionResponseDTO> create(@RequestBody SessionInputDTO session) {
        return ResponseEntity.ok(focusSessionService.create(session));
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String id) {
        return ResponseEntity.ok(focusSessionService.delete(id));
    }

    @GetMapping("/getSessions")
    public ResponseEntity<List<SessionResponseDTO>> getSessions() {
        return ResponseEntity.ok(focusSessionService.getSessions());
    }

    @GetMapping("/getSession")
    public ResponseEntity<SessionResponseDTO> getSession(@RequestParam String id) {
        return ResponseEntity.ok(focusSessionService.getSession(id));
    }
    @PostMapping("/pause")
    public ResponseEntity<SessionResponseDTO> pause(@RequestParam String id) {
        return ResponseEntity.ok(focusSessionService.pause(id));
    }

    @PostMapping("/resume")
    public ResponseEntity<SessionResponseDTO> resume(@RequestParam String id) {
        return ResponseEntity.ok(focusSessionService.resume(id));
    }

    @PostMapping("/complete")
    public ResponseEntity<SessionResponseDTO> complete(@RequestParam String id) {
        return ResponseEntity.ok(focusSessionService.complete(id));
    }
    @GetMapping("/streak")
    public ResponseEntity<Integer> getStreak() {
        return ResponseEntity.ok(focusSessionService.getCurrentStreak());
    }

}