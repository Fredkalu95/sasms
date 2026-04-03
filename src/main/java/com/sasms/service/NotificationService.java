package com.sasms.service;

import com.sasms.model.dto.NotificationResponse;
import com.sasms.model.entity.User;

import java.util.List;

public interface NotificationService {

    void sendNotification(User user, String title, String message);

    List<NotificationResponse> getUserNotifications(User user);

    void markAsRead(Long notificationId);
}
