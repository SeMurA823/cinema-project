package com.muravyev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class HallDto {
    @Positive
    private Long id;
    @NotEmpty
    private String name;
    private boolean active;
}
