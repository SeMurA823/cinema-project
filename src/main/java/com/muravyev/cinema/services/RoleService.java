package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.roles.UserRole;
import com.muravyev.cinema.entities.users.User;

import java.util.Collection;
import java.util.List;

public interface RoleService {
    UserRole setRole(User user, Role role);

    UserRole setRole(long userId, Role role);

    List<UserRole> getActiveRoles(long userId);

    void setRolesStatus(Collection<Long> ids, EntityStatus status);
}
