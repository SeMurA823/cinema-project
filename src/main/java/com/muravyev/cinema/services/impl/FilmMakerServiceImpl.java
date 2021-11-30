package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.FilmMakerDto;
import com.muravyev.cinema.dto.FilmMakerPostDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.film.FilmMaker;
import com.muravyev.cinema.entities.film.FilmMakerPost;
import com.muravyev.cinema.repo.FilmMakerPostRepository;
import com.muravyev.cinema.repo.FilmMakerRepository;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.services.FilmMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class FilmMakerServiceImpl implements FilmMakerService {
    private FilmMakerRepository makerRepository;
    private FilmMakerPostRepository postRepository;
    private FilmRepository filmRepository;

    @Autowired
    public void setPostRepository(FilmMakerPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setFilmRepository(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Autowired
    public void setMakerRepository(FilmMakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    @Override
    public FilmMaker addFilmMaker(FilmMakerDto filmMakerDto) {
        FilmMaker filmMaker = new FilmMaker();
        filmMaker.setFirstName(filmMakerDto.getFirstName());
        filmMaker.setLastName(filmMakerDto.getLastName());
        filmMaker.setPatronymic(filmMakerDto.getPatronymic());
        return makerRepository.save(filmMaker);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public FilmMakerPost setFilmMaker(FilmMakerPostDto makerPostDto) {
        Optional<FilmMakerPost> optionalPost = postRepository.findByFilmMakerIdAndFilmId(makerPostDto.getFilm(),
                makerPostDto.getMaker());
        if (optionalPost.isPresent())
            return optionalPost.get();
        Film film = filmRepository.findByIdAndEntityStatus(makerPostDto.getFilm(), EntityStatus.ACTIVE)
                .orElseThrow(EntityNotFoundException::new);
        FilmMaker maker = makerRepository.findByIdAndEntityStatus(makerPostDto.getMaker(), EntityStatus.ACTIVE)
                .orElseThrow(EntityNotFoundException::new);
        FilmMakerPost post = new FilmMakerPost();
        post.setFilmMaker(maker);
        post.setFilm(film);
        post.setName(makerPostDto.getPost());
        return postRepository.save(post);
    }

    @Override
    public void disableFilmMaker(long filmMakerId) {
        makerRepository.findByIdAndEntityStatus(filmMakerId, EntityStatus.ACTIVE)
                .ifPresent(maker -> {
                    maker.setEntityStatus(EntityStatus.NOT_ACTIVE);
                    makerRepository.save(maker);
                });
    }

    @Override
    public void disableFilmMakerPost(long filmId, long makerId) {
        postRepository.findByFilmIdAndFilmMakerIdAndEntityStatus(filmId, makerId, EntityStatus.ACTIVE)
                .ifPresent(post -> {
                    post.setEntityStatus(EntityStatus.NOT_ACTIVE);
                    postRepository.save(post);
                });
    }

    @Override
    public void removeFilmMaker(long filmMakerId) {
        makerRepository.findByIdAndEntityStatus(filmMakerId, EntityStatus.ACTIVE)
                .ifPresent(maker -> makerRepository.delete(maker));
    }

    @Override
    public void deleteFilmMakerPost(long filmId, long makerId) {
        postRepository.findByFilmIdAndFilmMakerIdAndEntityStatus(filmId, makerId, EntityStatus.ACTIVE)
                .ifPresent(post -> postRepository.delete(post));
    }

    @Override
    public FilmMaker getFilmMaker(long id) {
        return makerRepository.findByIdAndEntityStatus(id, EntityStatus.ACTIVE)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<FilmMakerPost> getFilmMakerPosts(long filmId) {
        return postRepository.findAllByFilmIdAndEntityStatus(filmId, EntityStatus.ACTIVE);
    }

    @Override
    public Page<FilmMakerPost> getAllPosts(long filmMakerId, Pageable pageable) {
        return postRepository.findAllByFilmMakerIdAndEntityStatus(filmMakerId, EntityStatus.ACTIVE, pageable);
    }
}
