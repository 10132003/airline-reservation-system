package com.thomas.airline.notification.controller;

import com.thomas.airline.notification.dto.NotificationRequestDto;
import com.thomas.airline.notification.dto.NotificationResponseDto;
import com.thomas.airline.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @PostMapping
    public ResponseEntity<NotificationResponseDto> createNotification(@Valid @RequestBody NotificationRequestDto requestDto){
        NotificationResponseDto responseDto=notificationService.createNotification(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getAllNotifications(){
        List<NotificationResponseDto> responseDtos=notificationService.getAllNotifications();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> getNotificationById(@PathVariable Long id){
        NotificationResponseDto responseDto=notificationService.getNotificationById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> updateNotification(@PathVariable Long id, @Valid @RequestBody NotificationRequestDto requestDto){
        NotificationResponseDto responseDto=notificationService.updateNotification(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String > deleteNotification(@PathVariable Long id){
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification is deleted successfully.");
    }
}
