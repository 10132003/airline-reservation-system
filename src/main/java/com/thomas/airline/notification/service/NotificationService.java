package com.thomas.airline.notification.service;

import com.thomas.airline.exception.NotificationNotFoundException;
import com.thomas.airline.exception.UserNotFoundException;
import com.thomas.airline.notification.dto.NotificationRequestDto;
import com.thomas.airline.notification.dto.NotificationResponseDto;
import com.thomas.airline.notification.entity.Notification;
import com.thomas.airline.notification.mapper.NotificationMapper;
import com.thomas.airline.notification.repository.NotificationRepository;
import com.thomas.airline.user.entity.User;
import com.thomas.airline.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationMapper notificationMapper, NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }
    public NotificationResponseDto createNotification(NotificationRequestDto requestDto){
        User user=userRepository.findById(requestDto.getUserId()).orElseThrow(()-> new UserNotFoundException("User is not available."));
        Notification notification=notificationMapper.requestDtoToNotification(requestDto);
        notification.setUser(user);
        notification.setCreatedAt(LocalDateTime.now());
        Notification savedNotification=notificationRepository.save(notification);
        NotificationResponseDto responseDto=notificationMapper.notificationToResponseDto(savedNotification);
        return responseDto;
    }
    public List<NotificationResponseDto> getAllNotifications(){
        List<Notification> notifications=notificationRepository.findAll();
        List<NotificationResponseDto> responseDtos=new ArrayList<>();
        for(Notification notification: notifications){
            NotificationResponseDto responseDto=notificationMapper.notificationToResponseDto(notification);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    public NotificationResponseDto getNotificationById(Long  id){
        Notification notification=notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException("Notification is not available."));
        NotificationResponseDto responseDto=notificationMapper.notificationToResponseDto(notification);
        return responseDto;
    }
    public NotificationResponseDto updateNotification(Long id, NotificationRequestDto requestDto){
        Notification notification=notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException("Notification is not available."));
        User user=userRepository.findById(requestDto.getUserId()).orElseThrow(()-> new UserNotFoundException("User is not available."));
        notification.setUser(user);
        notification.setTitle(requestDto.getTitle());
        notification.setMessage(requestDto.getMessage());
        notification.setNotificationType(requestDto.getNotificationType());
        Notification savedNotification=notificationRepository.save(notification);
        NotificationResponseDto responseDto=notificationMapper.notificationToResponseDto(savedNotification);
        return responseDto;
    }
    public void deleteNotification(Long id){
        Notification notification=notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException("Notification is not available."));
        notificationRepository.delete(notification);
    }
}
