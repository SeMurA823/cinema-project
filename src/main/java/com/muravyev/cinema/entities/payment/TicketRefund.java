package com.muravyev.cinema.entities.payment;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ticket_refunds")
@EntityListeners(AuditingEntityListener.class)
public class TicketRefund extends IdentityBaseEntity {
    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "canceled_by_user")
    private User canceledBy;
}
