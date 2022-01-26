package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.UserInfoDto;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.entities.users.UserStatus;
import com.muravyev.cinema.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping({"/api/users"})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<?> userInfo(Authentication authentication) {
        log.info("auth: {}", authentication);
        return ResponseEntity.ok((UserDetails) authentication.getPrincipal());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/edit", params = {"password"})
    public ResponseEntity<?> editPassword(@RequestBody String newPassword, Authentication authentication) {
        return ResponseEntity.ok(userService.editPassword(newPassword, (User) authentication.getPrincipal()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit")
    public ResponseEntity<?> editInfo(@RequestBody UserInfoDto userInfo, Authentication authentication) {
        return ResponseEntity.ok(userService.editUserInfo(userInfo, (User) authentication.getPrincipal()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<?> getAllUsers(@PageableDefault Pageable pageable) {
        Page<User> allUsers = userService.getAllUsers(pageable);
        return ResponseEntity.ok(allUsers);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        List<String> userStatusStrings = Arrays.stream(UserStatus.values())
                .map(Enum::name)
                .sorted()
                .collect(Collectors.toList());
        return ResponseEntity.ok(userStatusStrings);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/edit", params = {"status", "id"})
    public ResponseEntity<?> setStatus(@RequestParam("status") UserStatus status, @RequestParam("id") List<Long> ids) {
        userService.setUserStatus(status, ids);
        return ResponseEntity.ok()
                .build();
    }
}
