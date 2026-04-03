package com.sasms.controller;

import com.sasms.model.dto.NotificationResponse;
import com.sasms.model.entity.User;
import com.sasms.service.NotificationService;
import com.sasms.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService,
                                  UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<NotificationResponse> getMyNotifications(Authentication auth) {

        User user = userService.findByEmail(auth.getName());
        return notificationService.getUserNotifications(user);
    }

    @PutMapping("/{id}/read")
    @PreAuthorize("hasRole('USER')")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }
}
