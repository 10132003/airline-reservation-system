package com.thomas.airline.notification.mapper;

import com.thomas.airline.notification.dto.NotificationRequestDto;
import com.thomas.airline.notification.dto.NotificationResponseDto;
import com.thomas.airline.notification.entity.Notification;

import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notification requestDtoToNotification(NotificationRequestDto requestDto){
        Notification notification=new Notification();
        notification.setTitle(requestDto.getTitle());
        notification.setMessage(requestDto.getMessage());
        notification.setNotificationType(requestDto.getNotificationType());
        return notification;
    }
    public NotificationResponseDto notificationToResponseDto(Notification notification){
        NotificationResponseDto responseDto=new NotificationResponseDto();
        responseDto.setId(notification.getId());
        responseDto.setUserId(notification.getUser().getId());
        responseDto.setTitle(notification.getTitle());
        responseDto.setMessage(notification.getMessage());
        responseDto.setNotificationType(notification.getNotificationType());
        responseDto.setRead(notification.isRead());
        responseDto.setCreatedAt(notification.getCreatedAt());
        return responseDto;
    }
}
