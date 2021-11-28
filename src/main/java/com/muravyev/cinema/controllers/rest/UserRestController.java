package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.UserInfoDto;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> userInfo(Authentication authentication) {
        return ResponseEntity.ok((UserDetails)authentication.getPrincipal());
    }

    @PostMapping("/password/edit")
    public ResponseEntity<?> editPassword(@RequestParam("password") String newPassword, Authentication authentication) {
        return ResponseEntity.ok(userService.editPassword(newPassword, (User) authentication.getPrincipal()));
    }

    @PostMapping("/info/edit")
    public ResponseEntity<?> editInfo(@RequestBody UserInfoDto userInfo, Authentication authentication) {
        return ResponseEntity.ok(userService.editUserInfo(userInfo, (User) authentication.getPrincipal()));
    }
}
