package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.UserInfoDto;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.entities.users.UserStatus;
import com.muravyev.cinema.services.UserService;
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

@RestController
@RequestMapping({"/api/user", "/api/admin/users"})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> userInfo(Authentication authentication) {
        return ResponseEntity.ok((UserDetails) authentication.getPrincipal());
    }

    @PostMapping("/password/edit")
    public ResponseEntity<?> editPassword(@RequestParam("password") String newPassword, Authentication authentication) {
        return ResponseEntity.ok(userService.editPassword(newPassword, (User) authentication.getPrincipal()));
    }

    @PostMapping("/info/edit")
    public ResponseEntity<?> editInfo(@RequestBody UserInfoDto userInfo, Authentication authentication) {
        return ResponseEntity.ok(userService.editUserInfo(userInfo, (User) authentication.getPrincipal()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<?> getAllUsers(@PageableDefault Pageable pageable) {
        Page<User> allUsers = userService.getAllUsers(pageable);
        return ResponseEntity.ok(allUsers);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        List<String> userStatusStrings = Arrays.stream(UserStatus.values())
                .map(Enum::name)
                .sorted()
                .collect(Collectors.toList());
        return ResponseEntity.ok(userStatusStrings);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/edit")
    public ResponseEntity<?> setStatus(@RequestParam("status") UserStatus status, @RequestParam("id") List<Long> ids) {
        userService.setUserStatus(status, ids);
        return ResponseEntity.ok()
                .build();
    }
}
