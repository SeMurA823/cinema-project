package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.notifications.NotificationStatus;
import com.muravyev.cinema.entities.notifications.UserNotification;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.UserNotificationRepository;
import com.muravyev.cinema.services.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class NotificationServiceImpl implements NotificationService {
    private final UserNotificationRepository notificationRepository;

    public NotificationServiceImpl(UserNotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void notifyUser(String message, User user) {
        UserNotification notification = UserNotification.builder()
                .user(user)
                .message(message)
                .notificationStatus(NotificationStatus.NOT_VIEWED)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public void notifyUser(String message, long userId) {
        UserNotification notification = UserNotification.builder()
                .user(new User(){{
                    setId(userId);
                }})
                .message(message)
                .notificationStatus(NotificationStatus.NOT_VIEWED)
                .build();
        notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public void setViewedNotifications(List<Long> ids, User user) {
        notificationRepository.setViewedStatusByIdAndUser(ids, user);
    }

    @Override
    public List<UserNotification> getNotViewedNotifications(User user) {
        return notificationRepository
                .findAllByUserAndEntityStatusAndNotificationStatus(user,
                        EntityStatus.ACTIVE,
                        NotificationStatus.NOT_VIEWED,
                        Sort.by("created").descending());
    }

    @Override
    public Page<UserNotification> getAllNotifications(User user, Pageable pageable) {
        return getAllNotifications(user.getId(), pageable);
    }

    @Override
    public Page<UserNotification> getAllNotifications(long userId, Pageable pageable) {
        log.info("User ID = {}", userId);
        return notificationRepository
                .findAllByUserIdAndEntityStatus(userId, EntityStatus.ACTIVE, pageable);
    }

}
