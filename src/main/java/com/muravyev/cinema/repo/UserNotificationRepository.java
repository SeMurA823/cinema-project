package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.notifications.NotificationStatus;
import com.muravyev.cinema.entities.notifications.UserNotification;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    List<UserNotification> findAllByUserAndEntityStatusAndNotificationStatus(User user,
                                                                             EntityStatus entityStatus,
                                                                             NotificationStatus notificationStatus,
                                                                             Sort sort);

    Page<UserNotification> findAllByUserAndEntityStatus(User user, EntityStatus entityStatus, Pageable pageable);

    @Modifying
    @Query("update UserNotification un set un.notificationStatus = 'VIEWED' where un.user = :user and un.id in (:ids) and un.notificationStatus = 'NOT_VIEWED'")
    void setViewedStatusByIdAndUser(@Param("ids") List<Long> ids, @Param("user") User user);
}
