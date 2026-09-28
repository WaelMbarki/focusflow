package com.focusflow.demo.controller;

import com.focusflow.demo.dto.SessionInputDTO;
import com.focusflow.demo.dto.SessionResponseDTO;
import com.focusflow.demo.service.FocusSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}