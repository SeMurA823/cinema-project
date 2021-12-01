package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.session.ClientSession;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientSessionRepository extends JpaRepository<ClientSession, UUID> {
    List<ClientSession> findAllByUserAndEntityStatus(User user, EntityStatus entityStatus);
}
