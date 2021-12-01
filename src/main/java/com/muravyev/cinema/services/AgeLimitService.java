package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.AgeLimitDto;
import com.muravyev.cinema.entities.film.AgeLimit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AgeLimitService {
    @Transactional
    AgeLimit setAgeLimit(AgeLimitDto ageLimitDto);

    void deleteAgeLimit(String id);

    List<AgeLimit> getAgeLimits();
}
