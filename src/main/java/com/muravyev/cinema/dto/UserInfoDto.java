package com.muravyev.cinema.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class UserInfoDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotNull
    private String patronymic;
    @Past
    private LocalDate birthDate;
    private String gender;
    @NotEmpty
    private String tel;
}
