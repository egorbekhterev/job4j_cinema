package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Genre;

import java.util.Optional;

/**
 * Интерфейс для сервисов, работающих с жанрами {@link Genre}.
 * @author: Egor Bekhterev
 * @date: 23.02.2023
 * @project: job4j_cinema
 */
public interface GenreService {

    Optional<Genre> findById(int id);
}
