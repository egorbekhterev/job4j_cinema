package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FilmDto;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс для сервисов, работающих с фильмами {@link FilmDto}.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public interface FilmService {

    Optional<FilmDto> findById(int id);

    Collection<FilmDto> findAll();
}
