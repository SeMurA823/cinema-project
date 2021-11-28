package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.roles.UserRole;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    @Query("SELECT r FROM UserRole r WHERE r.entityStatus='ACTIVE' AND r.user.id = :userId")
    List<UserRole> findAllByUserId(@Param("userId") long userId);

    @Query("SELECT r FROM UserRole r " +
            "WHERE r.entityStatus='ACTIVE' AND r.user.id = :userId AND r.role = :role")
    UserRole findByUserIdAndRole(@Param("userId") long userId, @Param("role") Role role);

    default UserRole existsByUserAndRole(User user, Role role) {
        return findByUserIdAndRole(user.getId(), role);
    }

    default List<UserRole> findAllByUser(@Param("user") User user) {
        return findAllByUserId(user.getId());
    }
}
