package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.roles.UserRole;
import com.muravyev.cinema.entities.users.User;

public interface RoleService {
    UserRole setRole(User user, Role role);
}
