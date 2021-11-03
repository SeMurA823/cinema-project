package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.film.FilmPoster;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.services.FileWriterService;
import com.muravyev.cinema.services.FilmPosterService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;

@Service
public class FilmPosterServiceImpl implements FilmPosterService {
    private FileWriterService fileWriterService;
    private FilmRepository filmRepository;

    public FilmPosterServiceImpl(FileWriterService fileWriterService,
                                 FilmRepository filmRepository) {
        this.fileWriterService = fileWriterService;
        this.filmRepository = filmRepository;
    }

    @Override
    public String save(MultipartFile file, Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(EntityNotFoundException::new);
        return save(file, film);
    }

    @Override
    public String save(MultipartFile file, Film film) {
        FilmPoster poster = new FilmPoster();
        File wroteFile = fileWriterService.write(file);
        poster.setFilename(wroteFile.getName());
        poster.setFilm(film);
        film.getPosters().add(poster);
        filmRepository.save(film);
        return poster.getFilename();
    }
}
