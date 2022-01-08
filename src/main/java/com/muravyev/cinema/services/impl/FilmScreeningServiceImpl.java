package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.FilmScreeningDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.screening.FilmScreeningSeat;
import com.muravyev.cinema.events.Observable;
import com.muravyev.cinema.events.Observer;
import com.muravyev.cinema.events.*;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.repo.FilmScreeningRepository;
import com.muravyev.cinema.repo.FilmScreeningSeatRepository;
import com.muravyev.cinema.repo.HallRepository;
import com.muravyev.cinema.services.FilmScreeningService;
import com.muravyev.cinema.services.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.function.Consumer;

@Service
@Log4j2
public class FilmScreeningServiceImpl implements FilmScreeningService, Observer, Observable {
    private final FilmScreeningRepository screeningRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;
    private final FilmScreeningSeatRepository screeningSeatRepository;
    private final NotificationService notificationService;
    private final MessageSource messageSource;

    private NotificationManager notificationManager;

    private final Map<Class<? extends Event<?>>, Consumer<Event<?>>> eventActions = new HashMap<>() {{

        put(CancelFilmEvent.class, event -> cancelScreenings(((CancelFilmEvent) event).getValue()));

    }};

    public FilmScreeningServiceImpl(FilmScreeningRepository screeningRepository,
                                    FilmRepository filmRepository,
                                    HallRepository hallRepository,
                                    FilmScreeningSeatRepository screeningSeatRepository,
                                    NotificationService notificationService,
                                    MessageSource messageSource) {
        this.screeningRepository = screeningRepository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
        this.screeningSeatRepository = screeningSeatRepository;
        this.notificationService = notificationService;
        this.messageSource = messageSource;
    }

    @Autowired
    @Override
    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
        notificationManager.subscribe(this, eventActions.keySet());
    }

    @Override
    public Page<FilmScreening> getAllFilmScreening(long filmId, Pageable pageable) {
        return screeningRepository.findAllByFilmId(filmId, pageable);
    }

    @Override
    public void deleteFilmScreenings(List<Long> id) {
        screeningRepository.deleteAllById(id);
    }

    @Override
    public List<FilmScreening> getFilmScreeningsInDay(long filmId, Date date) {
        return screeningRepository.getActiveFilmScreeningsInDayInterval(filmId,
                getTodayDay(date),
                getNextDay(date));
    }

    @Override
    public FilmScreening setFilmScreening(long screeningId, FilmScreeningDto screeningDto) {
        FilmScreening filmScreening = merge(screeningDto,
                screeningRepository.findByIdAndDateAfter(screeningId, new Date())
                        .orElseThrow(EntityNotFoundException::new));
        FilmScreening savedScreening = screeningRepository.save(filmScreening);
        if (!savedScreening.isActive())
            notificationManager.notify(new CancelScreeningEvent(savedScreening), CancelScreeningEvent.class);
        return savedScreening;
    }

    private FilmScreening merge(FilmScreeningDto screeningDto, FilmScreening filmScreening) {
        Film film = filmRepository.findById(screeningDto.getFilmId())
                .orElseThrow(EntityNotFoundException::new);
        Hall hall = hallRepository.findById(screeningDto.getHallId())
                .orElseThrow(EntityNotFoundException::new);
        filmScreening.setFilm(film);
        filmScreening.setHall(hall);
        filmScreening.setDate(screeningDto.getDate());
        filmScreening.setPrice(screeningDto.getPrice());
        filmScreening.setEntityStatus(screeningDto.isActive() ? EntityStatus.ACTIVE : EntityStatus.NOT_ACTIVE);
        return filmScreening;
    }

    @Override
    @Transactional
    public FilmScreening addFilmScreening(FilmScreeningDto filmScreeningDto) {
        FilmScreening filmScreening = merge(filmScreeningDto, new FilmScreening());
        return screeningRepository.save(filmScreening);
    }

    @Override
    public List<FilmScreeningSeat> getStatusSeats(long screeningId) {
        return screeningSeatRepository.findAllByScreeningId(screeningId);
    }

    @Override
    public List<FilmScreeningSeat> getStatusSeats(FilmScreening filmScreening) {
        return screeningSeatRepository.findAllByScreening(filmScreening);
    }

    @Override
    public FilmScreening getFilmScreening(long id) {
        return screeningRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void setStatusScreenings(Collection<Long> ids, EntityStatus status) {
        List<FilmScreening> screenings =
                screeningRepository.findAllByIdInAndEntityStatusAndDateAfter(ids, EntityStatus.ACTIVE, new Date());
        screenings.forEach(x -> x.setEntityStatus(status));
        screeningRepository.saveAll(screenings);
        if (status.equals(EntityStatus.NOT_ACTIVE))
            screenings.stream()
                    .parallel()
                    .forEach(x->notify(new CancelScreeningEvent(x), CancelScreeningEvent.class));
    }

    @Override
    public Page<Film> getTodayFilms(Pageable pageable) {
        Date now = new Date();
        Date tomorrow = getNextDay(now);
        Date today = getTodayDay(now);
        return screeningRepository.findAllFilmsBetweenDates(today, tomorrow, EntityStatus.ACTIVE, pageable);
    }

    @Override
    public Film getFilmByScreening(long screeningId) {
        return screeningRepository.findByIdAndEntityStatus(screeningId, EntityStatus.ACTIVE)
                .map(FilmScreening::getFilm)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void cancelScreenings(Film film) {
        Date now = new Date();
        screeningRepository.streamAllByFilmAndDateAfterAndEntityStatus(film, now, EntityStatus.ACTIVE)
                .parallel()
                .peek(screening -> screening.setEntityStatus(EntityStatus.NOT_ACTIVE))
                .peek(screeningRepository::save)
                .forEach(screening -> notificationManager.notify(new CancelScreeningEvent(screening),
                        CancelScreeningEvent.class));

    }

    private Date getTodayDay(Date date) {
        return getCalendarDay(date).getTime();
    }

    private Calendar getCalendarDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    private Date getNextDay(Date date) {
        Calendar calendar = getCalendarDay(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    @Override
    public void notify(Event<?> event, Class<? extends Event<?>> eventType) {
        eventActions.get(eventType).accept(event);
    }
}
