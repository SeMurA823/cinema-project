package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.session.ClientEntity;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {
    List<ClientEntity> findAllByUserAndEntityStatus(User user, EntityStatus entityStatus);

    Optional<ClientEntity> findByIdAndEntityStatus(UUID id, EntityStatus entityStatus);

    @Modifying
    @Query("update ClientEntity cs set cs.entityStatus = 'NOT_ACTIVE' where cs.user = :user and cs.entityStatus = 'ACTIVE'")
    void disableAllSessionsByUser(@Param("user") User user);

    @Modifying
    @Query("update ClientEntity cs set cs.entityStatus = 'NOT_ACTIVE' where cs.id = :session and cs.entityStatus = 'ACTIVE'")
    void disableSessionById(@Param("session") UUID id);
}
