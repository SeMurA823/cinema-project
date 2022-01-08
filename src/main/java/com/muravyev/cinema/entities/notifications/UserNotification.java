package com.muravyev.cinema.entities.notifications;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_notifications")
public class UserNotification extends IdentityBaseEntity {
    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String message;

    @JsonIgnore
    @Column(name = "notification_status")
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    @Transient
    private boolean isViewed;

    @PostLoad
    private void checkViewed() {
        isViewed = notificationStatus.equals(NotificationStatus.VIEWED);
    }
}
