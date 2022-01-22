package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.FilmMaker;
import com.muravyev.cinema.entities.film.FilmMakerPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface FilmMakerPostRepository extends JpaRepository<FilmMakerPost, Long> {
    Optional<FilmMakerPost> findByIdAndEntityStatus(Long id, EntityStatus entityStatus);

    Optional<FilmMakerPost> findByFilmIdAndFilmMakerIdAndEntityStatus(Long filmId,
                                                                      Long filmMakerId,
                                                                      EntityStatus entityStatus);

    List<FilmMakerPost> findAllByFilmMaker(FilmMaker filmMaker);

    Optional<FilmMakerPost> findByFilmMakerIdAndFilmId(Long filmMakerId, Long filmId);

    List<FilmMakerPost> findAllByFilmIdAndEntityStatus(Long filmId, EntityStatus entityStatus);

    Page<FilmMakerPost> findAllByFilmMakerIdAndEntityStatus(Long filmMakerId,
                                                            EntityStatus entityStatus,
                                                            Pageable pageable);
    @Modifying
    void deleteAllByFilmMakerIdAndFilmIdAndName(Long filmMakerId, Long filmId, String post);
}
