package com.muravyev.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountStatisticsObject<T> {
    private T entity;
    private int count;
}
