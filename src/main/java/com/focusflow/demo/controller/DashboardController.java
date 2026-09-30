package com.focusflow.demo.controller;

import com.focusflow.demo.dto.DashbordSummaryDTO;
import com.focusflow.demo.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashbordSummaryDTO> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }
}