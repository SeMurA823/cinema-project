package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.PosterDto;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.film.FilmPoster;
import com.muravyev.cinema.repo.FilmPosterRepository;
import com.muravyev.cinema.services.FilmPosterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class FilmPosterServiceImpl implements FilmPosterService {

    @Value("${upload.default.path}")
    private String DEFAULT_PATH;

    private final FilmPosterRepository posterRepository;

    public FilmPosterServiceImpl(FilmPosterRepository posterRepository) {
        this.posterRepository = posterRepository;
    }

    @Override
    @Transactional
    public FilmPoster createPoster(PosterDto posterDto) {
        Film film = new Film();
        film.setId(posterDto.getFilmId());
        return createPoster(film, posterDto.getFileBase64());
    }

    private FilmPoster createPoster(Film film, String base64) {
        FilmPoster poster = new FilmPoster();
        poster.setFilm(film);
        String[] strings = base64.split(",");
        String meta = strings[0];
        int start = meta.indexOf("image/") + 6;
        int end = meta.indexOf(";base64");
        String filename = getFilename(meta.substring(start, end));
        poster.setFilename(filename);
        return save(poster, strings[1], filename);
    }

    private FilmPoster save(FilmPoster poster, String data, String filename) {
        File defaultDir = new File(DEFAULT_PATH);
        if (!defaultDir.exists())
            defaultDir.mkdir();
        File file = new File(DEFAULT_PATH + '/' + filename);
        try {
            posterRepository.save(poster);
            writeBase64ToFile(file, data);
            return poster;
        } catch (Exception e) {
            deleteFile(file);
            throw new RuntimeException(e);
        }
    }

    private void writeBase64ToFile(File file, String dataStr) {
        byte[] data = DatatypeConverter.parseBase64Binary(dataStr);
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    private void deleteFile(String filename) {
        deleteFile(new File(DEFAULT_PATH + '/' + filename));
    }

    private void deleteFile(File file) {
        file.delete();
    }

    private String getFilename(String ex) {
        return UUID.randomUUID().toString().replace("-", "") + '.' + ex;
    }


    @Override
    @Transactional
    public void delete(Iterable<Long> postersId) {
        posterRepository.findAllById(postersId).stream()
                .peek(posterRepository::delete)
                .forEach(poster -> this.deleteFile(poster.getFilename()));
    }

    @Override
    public List<FilmPoster> getPosters(Iterable<Long> id) {
        return posterRepository.findAllById(id);
    }

    @Override
    public Page<FilmPoster> getPosters(long filmId, Pageable pageable) {
        return posterRepository.findAllByFilmId(filmId, pageable);
    }

    @Override
    @Transactional
    public FilmPoster updatePoster(long posterId, PosterDto posterDto) {
        FilmPoster poster = posterRepository.findById(posterId)
                .orElseThrow(EntityNotFoundException::new);
        return save(poster, posterDto.getFileBase64(), poster.getFilename());
    }
}