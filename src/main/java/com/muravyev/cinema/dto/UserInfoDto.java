package com.muravyev.cinema.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserInfoDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthDate;
    private String gender;
    private String tel;
}
