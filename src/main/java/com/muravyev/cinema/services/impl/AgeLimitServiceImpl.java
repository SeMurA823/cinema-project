package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.AgeLimitDto;
import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.repo.AgeLimitRepository;
import com.muravyev.cinema.services.AgeLimitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgeLimitServiceImpl implements AgeLimitService {
    private final AgeLimitRepository ageLimitRepository;

    public AgeLimitServiceImpl(AgeLimitRepository ageLimitRepository) {
        this.ageLimitRepository = ageLimitRepository;
    }

    @Override
    @Transactional
    public List<AgeLimit> setAgeLimits(List<String> ageLimitId, AgeLimitDto ageLimitDto) {
        List<AgeLimit> allById = ageLimitRepository.findAllById(ageLimitId);
        allById.forEach(x -> setLimit(ageLimitDto, x));
        return ageLimitRepository.saveAll(allById);
    }

    @Override
    public AgeLimit createAgeLimit(AgeLimitDto ageLimitDto) {
        return ageLimitRepository.save(createLimit(ageLimitDto));
    }

    private AgeLimit createLimit(AgeLimitDto ageLimitDto) {
        return setLimit(ageLimitDto, new AgeLimit());
    }

    private AgeLimit setLimit(AgeLimitDto ageLimitDto, AgeLimit ageLimit) {
        ageLimit.setStartAge(ageLimitDto.getStartAge());
        ageLimit.setId(ageLimitDto.getName());
        ageLimit.setDescription(ageLimitDto.getDescription());
        return ageLimit;
    }

    @Override
    public void deleteAgeLimits(List<String> id) {
        ageLimitRepository.deleteAllById(id);
    }

    @Override
    public Page<AgeLimit> getAgeLimits(Pageable pageable) {
        return ageLimitRepository.findAll(pageable);
    }

    @Override
    public List<AgeLimit> getAgeLimits(List<String> id) {
        return ageLimitRepository.findAllById(id);
    }
}
