package com.muravyev.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    @CreatedDate
    @Column(name = "insert_date")
    private Date created;

    @LastModifiedDate
    @Column(name = "update_date")
    private Date updated;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus entityStatus = EntityStatus.ACTIVE;

    public BaseEntity() {
    }

    @PreUpdate
    private void update() {
        updated = new Date();
    }

    @PrePersist
    private void persist() {
        created = new Date();
    }

    public boolean isActive() {
        return entityStatus == EntityStatus.ACTIVE;
    }
}
