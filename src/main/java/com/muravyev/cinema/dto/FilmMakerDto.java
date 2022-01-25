package com.muravyev.cinema.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class FilmMakerDto {
    private long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String patronymic;
}
