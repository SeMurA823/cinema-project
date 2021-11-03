package com.muravyev.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    @JsonIgnore
    @Column(name = "insert_date")
    private Date insertDate;

    @JsonIgnore
    @Column(name = "update_date")
    private Date updateDate;

    @JsonIgnore
    @Column(name = "disable_date")
    private Date disableDate;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus entityStatus = EntityStatus.ENABLE;

    @PreUpdate
    private void setUpdateDate() {
        updateDate = new Date();
    }

    @PrePersist
    private void setInsertDate() {
        insertDate = new Date();
    }

}
