package com.muravyev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginDto {
    @Size(min = 6)
    private String username;
    @Size(min = 6)
    private String password;
}
