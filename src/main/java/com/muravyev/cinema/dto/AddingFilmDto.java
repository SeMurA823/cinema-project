package com.muravyev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

@Data
public class AddingFilmDto {
    @NotEmpty
    private String name;
    private Date localPremiere;
    private Date worldPremiere;
    @Positive
    private Long ageLimit;
    @NotEmpty
    private String plot;
    @NotEmpty
    private List<Long> countries;
}
