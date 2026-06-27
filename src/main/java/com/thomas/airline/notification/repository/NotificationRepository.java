package com.thomas.airline.notification.repository;

import com.thomas.airline.notification.entity.Notification;
import com.thomas.airline.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository  extends JpaRepository<Notification,Long> {
    List<Notification> findByUser(User user);
    List<Notification> findByUserAndIsReadFalse(User user);
    long countByUserAndIsReadFalse(User user);
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
