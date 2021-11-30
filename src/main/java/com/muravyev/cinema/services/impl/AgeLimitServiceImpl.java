package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.AgeLimitDto;
import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.repo.AgeLimitRepository;
import com.muravyev.cinema.services.AgeLimitService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgeLimitServiceImpl implements AgeLimitService {
    private AgeLimitRepository ageLimitRepository;

    public AgeLimitServiceImpl(AgeLimitRepository ageLimitRepository) {
        this.ageLimitRepository = ageLimitRepository;
    }

    @Override
    @Transactional
    public AgeLimit setAgeLimit(AgeLimitDto ageLimitDto) {
        AgeLimit ageLimit = ageLimitRepository.findById(ageLimitDto.getName())
                .orElse(new AgeLimit());
        ageLimit.setStartAge(ageLimitDto.getStartAge());
        ageLimit.setName(ageLimitDto.getName());
        ageLimit.setDescription(ageLimitDto.getDescription());
        return ageLimitRepository.save(ageLimit);
    }

    @Override
    public List<AgeLimit> getAgeLimits() {
        return ageLimitRepository.findAll(Sort.by("startAge").ascending());
    }
}
