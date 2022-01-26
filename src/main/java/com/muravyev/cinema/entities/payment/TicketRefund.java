package com.muravyev.cinema.entities.payment;

import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.users.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketRefund)) return false;
        if (!super.equals(o)) return false;
        TicketRefund that = (TicketRefund) o;
        return Objects.equals(ticket, that.ticket)
                && Objects.equals(canceledBy, that.canceledBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ticket, canceledBy);
    }
}
