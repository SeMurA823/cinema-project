package com.muravyev.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ScreeningTime {
    private String filmName;
    private Date start;
    private Date end;
}
