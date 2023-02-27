package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс для сервисов, работающих с киносеансами {@link FilmSession}, {@link FilmSessionDto}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
public interface FilmSessionService {

    Optional<FilmSession> findById(int id);

    Optional<FilmSessionDto> findByIdDto(int id);

    Collection<FilmSessionDto> findAll();
}
