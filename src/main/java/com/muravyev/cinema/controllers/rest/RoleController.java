package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.roles.UserRole;
import com.muravyev.cinema.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getActiveRoles(@RequestParam("user") long userId) {
        List<UserRole> activeRoles = roleService.getActiveRoles(userId);
        return ResponseEntity.ok(activeRoles);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/demote")
    public ResponseEntity<?> demote(@RequestParam("id") List<Long> ids) {
        roleService.setRolesStatus(ids, EntityStatus.NOT_ACTIVE);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("role") Role role, @RequestParam("user") long userId) {
        UserRole userRole = roleService.setRole(userId, role);
        return ResponseEntity.ok(userRole);
    }
}
