package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.NotificationFormDto;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.NotificationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class UserNotificationController {
    private final NotificationService notificationService;

    public UserNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(params = "new")
    public ResponseEntity<?> getNotViewedNotifications(Authentication authentication) {
        return ResponseEntity.ok(notificationService.getNotViewedNotifications((User) authentication.getPrincipal()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(params = {"size", "page"})
    public ResponseEntity<?> getAllNotifications(@PageableDefault(sort = "created", direction = Sort.Direction.DESC)
                                                         Pageable pageable,
                                                 Authentication authentication) {
        return ResponseEntity.ok(notificationService.getAllNotifications((User) authentication.getPrincipal(), pageable));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/viewed")
    public ResponseEntity<?> setViewed(@RequestBody List<Long> ids, Authentication authentication) {
        notificationService.setViewedNotifications(ids, (User) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(params = {"page", "size", "user"})
    public ResponseEntity<?> getAllNotifications(@PageableDefault(sort = "created", direction = Sort.Direction.DESC)
                                                         Pageable pageable,
                                                 @RequestParam("user") long userId) {
        return ResponseEntity.ok(notificationService.getAllNotifications(userId, pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createNotification(@RequestBody NotificationFormDto notification) {
        notificationService.notifyUser(notification.getMessage(), notification.getUser());
        return ResponseEntity.ok()
                .build();
    }
}
