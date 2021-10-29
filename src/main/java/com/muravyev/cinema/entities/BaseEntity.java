package com.muravyev.cinema.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "insert_date")
    private Date insertDate = new Date();

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "delete_date")
    private Date deleteDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus entityStatus = EntityStatus.ENABLE;

}
