package com.muravyev.cinema.controllers;

import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
public class UserAdminRestController {

    private final UserService userService;

    public UserAdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@PageableDefault Pageable pageable) {
        Page<User> allUsers = userService.getAllUsers(pageable);
        return ResponseEntity.ok(allUsers);
    }
}
