package com.muravyev.cinema.controllers;

import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/control")
public class AdminRestController {
    private final AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/appoint")
    public ResponseEntity<?> appointAdmin(long userId) {
        User user = adminService.appointAdmin(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{admin}/demote")
    public ResponseEntity<?> demoteAdmin(long userId) {
        User user = adminService.demoteAdmin(userId);
        return ResponseEntity.ok(user);
    }
}
