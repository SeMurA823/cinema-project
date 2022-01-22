package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.AgeLimitDto;
import com.muravyev.cinema.entities.film.AgeLimit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AgeLimitService {
    @Transactional
    List<AgeLimit> setAgeLimits(List<String> id, AgeLimitDto ageLimitDto);

    void deleteAgeLimits(List<String> id);

    AgeLimit createAgeLimit(AgeLimitDto ageLimitDto);

    Page<AgeLimit> getAgeLimits(Pageable pageable);

    List<AgeLimit> getAgeLimits();

    List<AgeLimit> getAgeLimits(List<String> id);
}
