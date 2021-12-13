package com.muravyev.cinema.dto;

import lombok.Data;

import java.util.Set;

@Data
public class HallDto {
    private Long id;
    private String name;
    private Set<Long> seatsId;
}
