package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.roles.UserRole;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.RoleRepository;
import com.muravyev.cinema.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public UserRole setRole(User user, Role role) {
        UserRole candidateUserRole = roleRepository.existsByUserAndRole(user, role);
        if (Objects.isNull(candidateUserRole)) {
            UserRole userRole = createSemiFinished();
            userRole.setRole(role);
            userRole.setUser(user);
            return roleRepository.save(userRole);
        } else {
            return candidateUserRole;
        }

    }

    private UserRole createSemiFinished() {
        UserRole userRole = new UserRole();
        userRole.setCreated(new Date());
        return userRole;
    }
}
