package com.muravyev.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    @JsonIgnore
    @Column(name = "insert_date")
    private Date insertDate = new Date();

    @JsonIgnore
    @Column(name = "update_date")
    private Date updateDate;

    @JsonIgnore
    @Column(name = "delete_date")
    private Date deleteDate;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus entityStatus = EntityStatus.ENABLE;

}
