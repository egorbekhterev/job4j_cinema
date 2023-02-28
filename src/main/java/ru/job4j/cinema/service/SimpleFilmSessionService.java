package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для киносеансов {@link FilmSessionDto}.
 * Сервис собирает данные для представления showtime/list.html.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
@ThreadSafe
@Service
public class SimpleFilmSessionService implements FilmSessionService {

    /**
     * Поле для обращения к хранилищу киносеансов.
     */
    private final FilmSessionRepository filmSessionRepository;

    /**
     * Поле для обращения к хранилищу фильмов.
     */
    private final FilmRepository filmRepository;

    /**
     * Поле для обращения к хранилищу залов.
     */
    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmSessionRepository,
                                    FilmRepository sql2oFilmRepository, HallRepository sql2oHallRepository) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.filmRepository = sql2oFilmRepository;
        this.hallRepository = sql2oHallRepository;
    }

    /**
     * Получает строковое представление названия фильма, используя FilmRepository.
     * Для экземпляра FilmSession доступно только id фильма.
     * @param filmSession - Экземпляр класса FilmSession {@link FilmSession}
     * @return Название фильма
     */
    private String getFilmName(FilmSession filmSession) {
        var filmOptional = filmRepository.findById(filmSession.getFilmId());
        if (filmOptional.isEmpty()) {
            return "No movie with this id is presented.";
        }
        return filmOptional.get().getName();
    }

    /**
     * Получает строковое представление названия кинозала, используя HallRepository.
     * Для экземпляра FilmSession доступно только id кинозала.
     * @param filmSession - Экземпляр класса FilmSession {@link FilmSession}
     * @return Название кинозала
     */
    private String getHallName(FilmSession filmSession) {
        var hallOptional = hallRepository.findById(filmSession.getHallId());
        if (hallOptional.isEmpty()) {
            return "No hall with this id is presented.";
        }
        return hallOptional.get().getName();
    }

    /**
     * Преобразует FilmSession в FilmSessionDto.
     * @param filmSession - Экземпляр класса FilmSession {@link FilmSession}
     * @return экземпляр FilmSessionDto {@link FilmSessionDto}
     */
    private FilmSessionDto transformFilmSessionToFilmSessionDto(FilmSession filmSession) {
        return new FilmSessionDto(filmSession.getId(), getFilmName(filmSession),
                getHallName(filmSession), filmSession.getStartTime(), filmSession.getEndTime(), filmSession.getPrice());
    }

    /**
     * Возвращает контейнер киносеанса {@link FilmSession}, найденного в таблице film_sessions по первому совпадению с ID.
     * @param id ID искомого киносеанса.
     * @return контейнер киносеанса {@link FilmSession}
     */
    @Override
    public Optional<FilmSession> findById(int id) {
        return filmSessionRepository.findById(id);
    }

    /**
     * Возвращает контейнер DTO-киносеанса {@link FilmSessionDto}, найденного в таблице film_sessions по первому совпадению с ID.
     * @param id ID искомого киносеанса.
     * @return контейнер DTO-фильма {@link FilmSessionDto}
     */
    @Override
    public Optional<FilmSessionDto> findByIdDto(int id) {
        var filmSessionOptional = filmSessionRepository.findById(id);
        if (filmSessionOptional.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(transformFilmSessionToFilmSessionDto(filmSessionOptional.get()));
    }

    /**
     * Преобразует ResultSet из таблицы film_sessions в коллекцию.
     * @return коллекция DTO-киносеансов {@link FilmSessionDto}.
     */
    @Override
    public Collection<FilmSessionDto> findAll() {
        return filmSessionRepository.findAll().stream()
                .map(this::transformFilmSessionToFilmSessionDto).collect(Collectors.toList());
    }
}
