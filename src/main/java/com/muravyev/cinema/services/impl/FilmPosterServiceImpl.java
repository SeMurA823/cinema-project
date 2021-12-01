package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.film.FilmPoster;
import com.muravyev.cinema.repo.FilmPosterRepository;
import com.muravyev.cinema.services.FileWriterService;
import com.muravyev.cinema.services.FilmPosterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;

@Service
public class FilmPosterServiceImpl implements FilmPosterService {
    private final FileWriterService fileWriterService;
    private final FilmPosterRepository posterRepository;

    public FilmPosterServiceImpl(FileWriterService fileWriterService,
                                 FilmPosterRepository posterRepository) {
        this.fileWriterService = fileWriterService;
        this.posterRepository = posterRepository;
    }

    @Override
    public FilmPoster save(MultipartFile file, Long filmId) {
        Film film = new Film();
        film.setId(filmId);
        return save(file, film);
    }

    @Override
    public FilmPoster save(MultipartFile file, Film film) {
        FilmPoster poster = new FilmPoster();
        File wroteFile = fileWriterService.write(file);
        poster.setFilename(wroteFile.getName());
        poster.setFilm(film);
        try {
            posterRepository.save(poster);
            return poster;
        } catch (Exception e) {
            fileWriterService.remove(wroteFile);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void delete(long posterId) {
        FilmPoster poster = posterRepository.findById(posterId)
                .orElseThrow(EntityNotFoundException::new);
        posterRepository.delete(poster);
        fileWriterService.remove(poster.getFilename());
    }
}
