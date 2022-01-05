package com.muravyev.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class StatisticsObject<T> {
    private T value;
    private Date start;
    private Date end;
}
