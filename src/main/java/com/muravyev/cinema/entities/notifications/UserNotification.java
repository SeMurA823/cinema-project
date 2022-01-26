package com.muravyev.cinema.entities.notifications;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

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

    @JsonProperty("created")
    @Override
    public Date getCreated() {
        return super.getCreated();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserNotification)) return false;
        if (!super.equals(o)) return false;
        UserNotification that = (UserNotification) o;
        return isViewed == that.isViewed
                && Objects.equals(user, that.user)
                && Objects.equals(message, that.message)
                && notificationStatus == that.notificationStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, message, notificationStatus, isViewed);
    }
}
