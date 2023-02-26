package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс для хранилищ фильмов {@link Film}.
 * @author: Egor Bekhterev
 * @date: 22.02.2023
 * @project: job4j_cinema
 */
public interface FilmRepository {

    Optional<Film> findById(int id);

    Collection<Film> getAll();
}
