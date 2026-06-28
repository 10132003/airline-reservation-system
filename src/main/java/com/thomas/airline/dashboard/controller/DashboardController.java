package com.thomas.airline.dashboard.controller;

import com.thomas.airline.dashboard.dto.DashboardResponseDto;
import com.thomas.airline.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<DashboardResponseDto> getDashboardStatistics(){
        DashboardResponseDto responseDto=dashboardService.getDashboardStatistics();
        return ResponseEntity.ok(responseDto);
    }
}
